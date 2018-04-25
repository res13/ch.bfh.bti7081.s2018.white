package ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.App;

import java.util.List;

public class GoalTracker extends App {

    private List<Goal> goalList;

    public GoalTracker() {
        name = MessageHandler.GOAL_TRACKER_NAME;
    }

}
