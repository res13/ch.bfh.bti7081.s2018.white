package ch.bfh.bti7081.s2018.white.pms.services.impl;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.*;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Patient;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Relative;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import ch.bfh.bti7081.s2018.white.pms.common.util.PasswordHasher;
import ch.bfh.bti7081.s2018.white.pms.persistence.JpaUtility;
import junit.framework.TestCase;

import java.time.LocalDateTime;
import java.util.List;

public class GoalServiceImplTest extends TestCase {

    private GoalServiceImpl goalGoalService;
    private Goal testGoal1;
    private Relative testRelative;


    @Override
    protected void setUp() throws Exception {
        super.setUp();

        testRelative = new Relative();
        goalGoalService = new GoalServiceImpl();
        testGoal1 = new Goal();

        testGoal1.setCreator(testRelative);
        testGoal1.setCreated(LocalDateTime.now());
        testGoal1.setDueTo(LocalDateTime.now().plusDays(1).plusHours(10).plusMinutes(30));
        testGoal1.setGoal("GoalTest1");
        testGoal1.setGoalTracker(new GoalTracker());
        testGoal1.setCreator(testRelative);
        new JpaUtility().execute(
                (em) -> {
                    em.persist(testGoal1);
                    return null;
                });
    }


    public void GoalGetterServiceCheck(){
        try
        {
            List<Goal> testGoals = goalGoalService.getGoalEntriesForUser(testRelative);
            assertEquals("Goal correctly stored", testGoals.get(0).equals(testGoal1));
        } catch (Exception e) {
            fail("Goal storage test for relative failed");
        }
    }


    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        new JpaUtility().execute(
                (em) -> {
                    Goal testGoal1 = em.find(Goal.class, testRelative);
                    em.remove(testGoal1);
                    return null;
                });
    }
}
