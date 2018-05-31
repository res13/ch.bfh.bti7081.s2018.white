package ch.bfh.bti7081.s2018.white.pms.services.impl;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.Goal;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.GoalTracker;
import ch.bfh.bti7081.s2018.white.pms.common.model.caze.Caze;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Doctor;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Patient;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Relative;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
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

    public List<Goal> getPatientGoalEntriesForUser(User user) {
        return getGoalEntriesForUser(user)
                .stream()
                .filter(goalEntry1 -> goalEntry1.getCreator() instanceof Patient)
                .collect(Collectors.toList());
    }

    public List<Goal> getRelativeGoalEntriesForUser(User user) {
        return getGoalEntriesForUser(user)
                .stream()
                .filter(goalEntry1 -> goalEntry1.getCreator() instanceof Relative)
                .collect(Collectors.toList());
    }

    private List<Goal> getGoalEntriesForUser(User user) {
        return new JpaUtility().execute(
                (em) -> {
                    CriteriaBuilder cb = em.getCriteriaBuilder();
                    CriteriaQuery<Goal> cq = cb.createQuery(clazz);
                    Root<Goal> goalEntry = cq.from(clazz);
                    Join<Goal, GoalTracker> goalToGoalJoin = goalEntry.join("goalTracker");
                    Join<GoalTracker, Caze> cazeToGoalTrackerJoin = goalToGoalJoin.join("caze");
                    Join<Caze, Patient> patientToCazeJoin = cazeToGoalTrackerJoin.join("patient");
                    CriteriaQuery<Goal> where = null;
                    if (user instanceof Relative) {
                        Join<Patient, Relative> relativeToPatientJoin = patientToCazeJoin.join("relativeList");
                        where = cq.where(cb.equal(relativeToPatientJoin.get("id"), user.getId()));
                    } else if (user instanceof Patient) {
                        where = cq.where(cb.equal(patientToCazeJoin.get("id"), user.getId()));
                    } else if (user instanceof Doctor) {
                        Join<Patient, Doctor> doctorToPatientJoin = patientToCazeJoin.join("doctorList");
                        where = cq.where(cb.equal(doctorToPatientJoin.get("id"), user.getId()));
                    }
                    TypedQuery<Goal> allQuery = em.createQuery(where);
                    return allQuery.getResultList();
                });
    }
}
