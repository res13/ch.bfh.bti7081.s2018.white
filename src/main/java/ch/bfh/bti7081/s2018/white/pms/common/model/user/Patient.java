package ch.bfh.bti7081.s2018.white.pms.common.model.user;

import ch.bfh.bti7081.s2018.white.pms.common.model.caze.Case;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "patient")
public class Patient extends User {

    @ManyToMany
    private List<Relative> relativeList;

    @ManyToMany
    private List<Doctor> doctorList;

    @OneToMany
    private List<Case> caseList;

}
