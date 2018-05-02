package ch.bfh.bti7081.s2018.white.pms.services;

import ch.bfh.bti7081.s2018.white.pms.common.model.PmsEntity;

import java.util.List;

public interface BaseService<T extends PmsEntity> {

    void saveOrUpdateEntity(T entity) throws Exception;

    void deleteEntity(T entity) throws Exception;

    List<T> getAllEntities() throws Exception;

    T getEntityById(long id) throws Exception;

}
