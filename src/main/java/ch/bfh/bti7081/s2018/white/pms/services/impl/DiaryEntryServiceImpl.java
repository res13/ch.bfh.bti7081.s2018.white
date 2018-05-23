package ch.bfh.bti7081.s2018.white.pms.services.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry;
import ch.bfh.bti7081.s2018.white.pms.persistence.JpaUtility;
import ch.bfh.bti7081.s2018.white.pms.services.DiaryEntryService;

public class DiaryEntryServiceImpl extends BaseServiceImpl<DiaryEntry> implements DiaryEntryService {

    public DiaryEntryServiceImpl() {
        super(DiaryEntry.class);
    }
    
    public List<DiaryEntry> getEntitiesByCreatorId(long id) throws Exception {
        return (List<DiaryEntry>) new JpaUtility().execute(
                (em) -> {
                    CriteriaBuilder cb = em.getCriteriaBuilder();
                    CriteriaQuery<DiaryEntry> cq = cb.createQuery(clazz);
                    Root<DiaryEntry> rootEntry = cq.from(clazz);
                    CriteriaQuery<DiaryEntry> where = cq.where(cb.equal(rootEntry.get("creator"), id));
                    TypedQuery<DiaryEntry> allQuery = em.createQuery(where);
                    return allQuery.getResultList();
                });
    }
    
    public List<DiaryEntry> getEntitiesByDiaryId(long id) throws Exception {
        return (List<DiaryEntry>) new JpaUtility().execute(
                (em) -> {
                    CriteriaBuilder cb = em.getCriteriaBuilder();
                    CriteriaQuery<DiaryEntry> cq = cb.createQuery(clazz);
                    Root<DiaryEntry> rootEntry = cq.from(clazz);
                    CriteriaQuery<DiaryEntry> where = cq.where(cb.equal(rootEntry.get("diary"), id));
                    TypedQuery<DiaryEntry> allQuery = em.createQuery(where);
                    return allQuery.getResultList();
                });
    }
    
}
