package ch.bfh.bti7081.s2018.white.pms.services.impl;

import ch.bfh.bti7081.s2018.white.pms.common.model.PmsType;
import ch.bfh.bti7081.s2018.white.pms.persistence.JpaUtility;
import ch.bfh.bti7081.s2018.white.pms.services.BaseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class BaseServiceImpl<T extends PmsType> implements BaseService<T> {

    public static final Logger log = LogManager.getLogger(BaseServiceImpl.class.getName());

    private Class<T> clazz;

    public BaseServiceImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void createEntity(T entity) throws Exception {
        try (JpaUtility jpaUtility = new JpaUtility()) {
            jpaUtility.getEntityManager().persist(entity);
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Override
    public void saveOrUpdateEntity(T entity) throws Exception {
        try (JpaUtility jpaUtility = new JpaUtility()) {
            jpaUtility.getEntityManager().persist(entity);
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Override
    public void deleteEntity(T entity) throws Exception {
        try (JpaUtility jpaUtility = new JpaUtility()) {
            jpaUtility.getEntityManager().remove(entity);
        } catch (Exception e) {
            log.error(e);
        }
    }

    @Override
    public List<T> getAllEntities() throws Exception {
        try (JpaUtility jpaUtility = new JpaUtility()) {
            CriteriaBuilder cb = jpaUtility.getEntityManager().getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(clazz);
            Root<T> rootEntry = cq.from(clazz);
            CriteriaQuery<T> all = cq.select(rootEntry);
            TypedQuery<T> allQuery = jpaUtility.getEntityManager().createQuery(all);
            return allQuery.getResultList();
        } catch (Exception e) {
            log.error(e);
            throw e;
        }
    }

    @Override
    public T getEntityById(long id) throws Exception {
        try (JpaUtility jpaUtility = new JpaUtility()) {
            return jpaUtility.getEntityManager().find(clazz, id);
        } catch (Exception e) {
            log.error(e);
            throw e;
        }
    }
}
