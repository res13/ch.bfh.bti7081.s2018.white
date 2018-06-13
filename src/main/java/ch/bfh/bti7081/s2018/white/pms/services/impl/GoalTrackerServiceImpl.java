package ch.bfh.bti7081.s2018.white.pms.services.impl;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.GoalTracker;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.GoalTracker_;
import ch.bfh.bti7081.s2018.white.pms.common.model.caze.Caze;
import ch.bfh.bti7081.s2018.white.pms.common.model.caze.Caze_;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Patient;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Patient_;
import ch.bfh.bti7081.s2018.white.pms.persistence.JpaUtility;
import ch.bfh.bti7081.s2018.white.pms.services.GoalTrackerService;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

public class GoalTrackerServiceImpl extends AppServiceImpl<GoalTracker> implements GoalTrackerService {

    public GoalTrackerServiceImpl() {
        super(GoalTracker.class);
    }

    public GoalTracker getGoalTrackerByPatientEntityId(long id) throws Exception {
        return new JpaUtility().execute(
                (em) -> {
                    CriteriaBuilder cb = em.getCriteriaBuilder();
                    CriteriaQuery<GoalTracker> cq = cb.createQuery(clazz);
                    Root<GoalTracker> goalTracker = cq.from(clazz);
                    Join<GoalTracker, Caze> cazeToDiaryJoin = goalTracker.join(GoalTracker_.caze);
                    Join<Caze, Patient> patientToCazeJoin = cazeToDiaryJoin.join(Caze_.patient);
                    CriteriaQuery<GoalTracker> where = cq.where(cb.equal(patientToCazeJoin.get(Patient_.id), id));
                    TypedQuery<GoalTracker> allQuery = em.createQuery(where);
                    return allQuery.getSingleResult();
                });
    }
}
