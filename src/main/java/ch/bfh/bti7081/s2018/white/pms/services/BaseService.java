package ch.bfh.bti7081.s2018.white.pms.services;

import ch.bfh.bti7081.s2018.white.pms.common.model.PmsType;

import java.util.List;

public interface BaseService<T extends PmsType> {

    void createEntity(T entity);

    void saveOrUpdateEntity(T entity);

    void deleteEntity(T entity);

    List<T> getAllEntities();

    T getEntityById(long id);

}
