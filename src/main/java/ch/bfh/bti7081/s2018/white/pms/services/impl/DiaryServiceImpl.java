package ch.bfh.bti7081.s2018.white.pms.services.impl;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.Diary;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.Diary_;
import ch.bfh.bti7081.s2018.white.pms.common.model.caze.Caze;
import ch.bfh.bti7081.s2018.white.pms.common.model.caze.Caze_;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Patient;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Patient_;
import ch.bfh.bti7081.s2018.white.pms.persistence.JpaUtility;
import ch.bfh.bti7081.s2018.white.pms.services.DiaryService;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

public class DiaryServiceImpl extends AppServiceImpl<Diary> implements DiaryService {

    public DiaryServiceImpl() {
        super(Diary.class);
    }

    public Diary getDiaryByPatientEntityId(long id) throws Exception {
        return new JpaUtility().execute(
                (em) -> {
                    CriteriaBuilder cb = em.getCriteriaBuilder();
                    CriteriaQuery<Diary> cq = cb.createQuery(clazz);
                    Root<Diary> diary = cq.from(clazz);
                    Join<Diary, Caze> cazeToDiaryJoin = diary.join(Diary_.caze);
                    Join<Caze, Patient> patientToCazeJoin = cazeToDiaryJoin.join(Caze_.patient);
                    CriteriaQuery<Diary> where = cq.where(cb.equal(patientToCazeJoin.get(Patient_.id), id));
                    TypedQuery<Diary> allQuery = em.createQuery(where);
                    return allQuery.getSingleResult();
                });
    }
}
