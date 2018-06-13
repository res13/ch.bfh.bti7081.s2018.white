package ch.bfh.bti7081.s2018.white.pms.services.impl;

import ch.bfh.bti7081.s2018.white.pms.common.model.user.Relative;
import ch.bfh.bti7081.s2018.white.pms.services.RelativeService;

public class RelativeServiceImpl extends UserServiceImpl<Relative> implements RelativeService {

    public RelativeServiceImpl() {
        super(Relative.class);
    }

}
