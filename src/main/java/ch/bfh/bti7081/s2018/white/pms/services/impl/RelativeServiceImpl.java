package ch.bfh.bti7081.s2018.white.pms.services.impl;

import java.util.List;

import ch.bfh.bti7081.s2018.white.pms.common.model.user.*;
import ch.bfh.bti7081.s2018.white.pms.services.RelativeService;

public class RelativeServiceImpl extends UserServiceImpl<Relative> implements RelativeService {

    public RelativeServiceImpl() {
        super(Relative.class);
    }
    
    public List<Patient> getListOfRelativesAssociatedPatients(User user){
		return null;
    	
    }
}
