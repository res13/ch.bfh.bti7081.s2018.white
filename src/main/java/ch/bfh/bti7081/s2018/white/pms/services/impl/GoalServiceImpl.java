package ch.bfh.bti7081.s2018.white.pms.services.impl;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.Goal;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.GoalTracker;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.GoalTracker_;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.Goal_;
import ch.bfh.bti7081.s2018.white.pms.common.model.caze.Caze;
import ch.bfh.bti7081.s2018.white.pms.common.model.caze.Caze_;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.*;
import ch.bfh.bti7081.s2018.white.pms.persistence.JpaUtility;
import ch.bfh.bti7081.s2018.white.pms.services.GoalService;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

public class GoalServiceImpl extends BaseServiceImpl<Goal> implements GoalService {

    public GoalServiceImpl() {
        super(Goal.class);
    }

    public List<Goal> getGoalEntriesForUser(User user) {
        return new JpaUtility().execute(
                (em) -> {
                    CriteriaBuilder cb = em.getCriteriaBuilder();
                    CriteriaQuery<Goal> cq = cb.createQuery(clazz);
                    Root<Goal> goalEntry = cq.from(clazz);
                    Join<Goal, GoalTracker> goalToGoalJoin = goalEntry.join(Goal_.goalTracker);
                    Join<GoalTracker, Caze> cazeToGoalTrackerJoin = goalToGoalJoin.join(GoalTracker_.caze);
                    Join<Caze, Patient> patientToCazeJoin = cazeToGoalTrackerJoin.join(Caze_.patient);
                    CriteriaQuery<Goal> where = null;
                    if (user instanceof Relative) {
                        Join<Patient, Relative> relativeToPatientJoin = patientToCazeJoin.join(Patient_.relativeList);
                        where = cq.where(cb.equal(relativeToPatientJoin.get(Relative_.id), user.getId()));
                    } else if (user instanceof Patient) {
                        where = cq.where(cb.equal(patientToCazeJoin.get(Patient_.id), user.getId()));
                    } else if (user instanceof Doctor) {
                        Join<Patient, Doctor> doctorToPatientJoin = patientToCazeJoin.join(Patient_.doctorList);
                        where = cq.where(cb.equal(doctorToPatientJoin.get(Doctor_.id), user.getId()));
                    }
                    TypedQuery<Goal> allQuery = em.createQuery(where);
                    return allQuery.getResultList();
                });
    }
    
    public List<Goal> getGoalEntriesForUserAndFilter(User user, String like) {
        return getGoalEntriesForUser(user)
                .stream()
                .filter(goal1 -> goal1.getGoal().toLowerCase().contains(like.toLowerCase()))
                .collect(Collectors.toList());
    }
}
