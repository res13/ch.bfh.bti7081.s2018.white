package ch.bfh.bti7081.s2018.white.pms.common.model.user;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.goal.Goal;

import java.util.List;

public class Patient extends User {

    private List<Doctor> doctorList;

    private List<Relative> relativeList;

    private List<Goal> goalList;
}
