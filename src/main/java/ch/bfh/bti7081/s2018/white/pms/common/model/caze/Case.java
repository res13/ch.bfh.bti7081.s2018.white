package ch.bfh.bti7081.s2018.white.pms.common.model.caze;

import ch.bfh.bti7081.s2018.white.pms.common.model.PmsType;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.App;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Patient;

import java.time.LocalDateTime;
import java.util.List;

public class Case extends PmsType {

    private Patient patient;

    private LocalDateTime from;

    private LocalDateTime to;

    private String note;

    private List<App> appList;

}
