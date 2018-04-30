package ch.bfh.bti7081.s2018.white.pms.services.impl;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.Goal;
import ch.bfh.bti7081.s2018.white.pms.services.GoalService;

public class GoalServiceImpl extends BaseServiceImpl<Goal> implements GoalService {

    public GoalServiceImpl() {
        super(Goal.class);
    }
}
