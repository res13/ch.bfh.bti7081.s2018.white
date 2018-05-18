package ch.bfh.bti7081.s2018.white.pms.services.impl;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import ch.bfh.bti7081.s2018.white.pms.common.util.PasswordHasher;
import ch.bfh.bti7081.s2018.white.pms.persistence.JpaUtility;
import ch.bfh.bti7081.s2018.white.pms.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserServiceImpl<T extends User> extends BaseServiceImpl<T> implements UserService {

    public static final Logger log = LogManager.getLogger(UserServiceImpl.class.getName());

    public UserServiceImpl(Class<T> clazz) {
        super(clazz);
    }

    public UserServiceImpl() {
        this((Class<T>) User.class);
    }

    @Override
    public User authenticate(String email, String password) throws Exception {
        List<T> userList = getUserByEmail(email);
        if (userList.size() == 1) {
            User user = userList.get(0);
            if (PasswordHasher.validate(password, user.getSalt(), user.getPasswordHash())) {
                return user;
            } else {
                log.info("User " + email + "entered a wrong password");
                throw new Exception(MessageHandler.USERNAME_OR_PASSWORD_INCORRECT);
            }
        } else {
            log.info("User " + email + "not found");
            throw new Exception(MessageHandler.USERNAME_OR_PASSWORD_INCORRECT);
        }
    }

    private List<T> getUserByEmail(String email) {
        return new JpaUtility().execute(
                (em) -> {
                    CriteriaBuilder cb = em.getCriteriaBuilder();
                    CriteriaQuery<T> cq = cb.createQuery(clazz);
                    Root<T> rootEntry = cq.from(clazz);
                    CriteriaQuery<T> where = cq.where(cb.equal(rootEntry.get("email"), email));
                    TypedQuery<T> allQuery = em.createQuery(where);
                    return allQuery.getResultList();
                });
    }

    @Override
    public T saveOrUpdateEntity(T entity) throws Exception {
        // if the user is persisted to the database at first time
        if (entity.getId() == null && entity.getLastModified() == null) {
            List<T> userList = getUserByEmail(entity.getEmail());
            if (userList.size() != 0) {
                log.info("Email " + entity.getEmail() + "is already in use");
                throw new Exception(MessageHandler.EMAIL_ALREADY_IN_USE);
            }
        }
        return super.saveOrUpdateEntity(entity);
    }
}
