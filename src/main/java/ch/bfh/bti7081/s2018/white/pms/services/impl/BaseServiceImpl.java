package ch.bfh.bti7081.s2018.white.pms.services.impl;

import ch.bfh.bti7081.s2018.white.pms.common.model.PmsType;
import ch.bfh.bti7081.s2018.white.pms.services.BaseService;
import ch.bfh.bti7081.s2018.white.pms.services.hibernate.HibernateUtil;

import java.util.List;

public class BaseServiceImpl<T extends PmsType> implements BaseService<T> {

    @Override
    public void createEntity(T entity) {
        HibernateUtil.getSession().save(entity);
    }

    @Override
    public void saveOrUpdateEntity(T entity) {
        HibernateUtil.getSession().saveOrUpdate(entity);
    }

    @Override
    public void deleteEntity(T entity) {
        HibernateUtil.getSession().remove(entity);
    }

    @Override
    public List<T> getAllEntities() {
        return null;
    }

    @Override
    public T getEntityById(long id) {
        return null;
    }
}
