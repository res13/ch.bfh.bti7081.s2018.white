package ch.bfh.bti7081.s2018.white.pms.common.model.user;

import ch.bfh.bti7081.s2018.white.pms.common.model.caze.Case;

import java.util.List;

public class Patient extends User {

    private List<Relative> relativeList;

    private List<Doctor> doctorList;

    private List<Case> caseList;

}
