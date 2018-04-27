package ch.bfh.bti7081.s2018.white.pms.common.model.caze;

import ch.bfh.bti7081.s2018.white.pms.common.model.PmsType;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.App;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Patient;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Case extends PmsType {

    @ManyToOne
    private Patient patient;

    private LocalDateTime from;

    private LocalDateTime to;

    private String note;

    @OneToMany
    @JoinColumn(name="caze_id")
    private List<App> appList;

}
