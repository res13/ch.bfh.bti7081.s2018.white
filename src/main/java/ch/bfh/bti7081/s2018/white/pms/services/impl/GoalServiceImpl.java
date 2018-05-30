package ch.bfh.bti7081.s2018.white.pms.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.Goal;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Patient;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import ch.bfh.bti7081.s2018.white.pms.persistence.JpaUtility;
import ch.bfh.bti7081.s2018.white.pms.services.GoalService;

public class GoalServiceImpl extends BaseServiceImpl<Goal> implements GoalService {

    public GoalServiceImpl() {
        super(Goal.class);
    }
    
    public List<Goal> getListOfRelativesAssociatedPatientsGoals(User user) {
        return null;
            /*    .stream()
                .filter(diaryEntry1 -> diaryEntry1.getCreator() instanceof Patient)
                .collect(Collectors.toList());*/
    }
    
    public Goal getGoalByEntityId(long id) throws Exception {
        return new JpaUtility().execute(
                (em) -> {
                    CriteriaBuilder cb = em.getCriteriaBuilder();
                    CriteriaQuery<Goal> cq = cb.createQuery(clazz);
                    Root<Goal> goal = cq.from(clazz);
                    Join<Goal, Patient> goalToPatientJoin = goal.join("patient");
                    CriteriaQuery<Goal> where = cq.where(cb.equal(goalToPatientJoin.get("id"), id));
                    TypedQuery<Goal> allQuery = em.createQuery(where);
                    return allQuery.getSingleResult();
                });
    }
}
