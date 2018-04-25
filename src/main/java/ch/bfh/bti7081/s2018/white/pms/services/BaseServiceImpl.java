package ch.bfh.bti7081.s2018.white.pms.services;

import ch.bfh.bti7081.s2018.white.pms.common.model.PmsType;

import java.util.List;

public class BaseServiceImpl<T extends PmsType> implements BaseService<T> {

    @Override
    public void createEntity(T entity) {

    }

    @Override
    public void saveOrUpdateEntity(T entity) {

    }

    @Override
    public void deleteEntity(T entity) {

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
