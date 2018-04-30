package ch.bfh.bti7081.s2018.white.pms.services.impl;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.GoalTracker;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Doctor;
import ch.bfh.bti7081.s2018.white.pms.services.GoalTrackerService;

public class GoalTrackerServiceImpl extends AppServiceImpl<GoalTracker> implements GoalTrackerService {

    public GoalTrackerServiceImpl() {
        super(GoalTracker.class);
    }
}
