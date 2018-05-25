package ch.bfh.bti7081.s2018.white.pms.services.impl;

import ch.bfh.bti7081.s2018.white.pms.common.model.PmsEntity;
import ch.bfh.bti7081.s2018.white.pms.persistence.JpaUtility;
import ch.bfh.bti7081.s2018.white.pms.services.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class BaseServiceImpl<T extends PmsEntity> implements BaseService<T> {

    public static final Logger log = LogManager.getLogger(BaseServiceImpl.class.getName());

    protected Class<T> clazz;

    public BaseServiceImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T saveOrUpdateEntity(T entity) throws Exception {
        return new JpaUtility().execute(
                (em) -> em.merge(entity));
    }

    @Override
    public void deleteEntity(T entity) throws Exception {
        new JpaUtility().execute(
                (em) -> {
                    T entityToDelete = em.find(clazz, entity.getId());
                    em.remove(entityToDelete);
                    return null;
                });
    }

    @Override
    public List<T> getAllEntities() throws Exception {
        return new JpaUtility().execute(
                (em) -> {
                    CriteriaBuilder cb = em.getCriteriaBuilder();
                    CriteriaQuery<T> cq = cb.createQuery(clazz);
                    Root<T> rootEntry = cq.from(clazz);
                    CriteriaQuery<T> all = cq.select(rootEntry);
                    TypedQuery<T> allQuery = em.createQuery(all);
                    return allQuery.getResultList();
                });
    }

    @Override
    public T getEntityById(long id) throws Exception {
        return new JpaUtility().execute(
                (em) -> {
                    return em.find(clazz, id);
                });
    }
}
