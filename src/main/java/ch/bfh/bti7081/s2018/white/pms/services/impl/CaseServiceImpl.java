package ch.bfh.bti7081.s2018.white.pms.services.impl;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.Goal;
import ch.bfh.bti7081.s2018.white.pms.common.model.caze.Case;
import ch.bfh.bti7081.s2018.white.pms.services.CaseService;

public class CaseServiceImpl extends BaseServiceImpl<Case> implements CaseService {

    public CaseServiceImpl() {
        super(Case.class);
    }

}
