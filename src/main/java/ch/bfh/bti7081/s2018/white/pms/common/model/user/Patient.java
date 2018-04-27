package ch.bfh.bti7081.s2018.white.pms.common.model.user;

import ch.bfh.bti7081.s2018.white.pms.common.model.caze.Case;

import javax.persistence.*;
import java.util.List;

@Entity
public class Patient extends User {

    @ManyToMany
    @JoinTable(name = "Patient_Relative",
            joinColumns = @JoinColumn(name = "Relative_id"),
            inverseJoinColumns = @JoinColumn(name = "Patient_id")
    )
    private List<Relative> relativeList;

    @ManyToMany
    @JoinTable(name = "Patient_Doctor",
            joinColumns = @JoinColumn(name = "Doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "Patient_id")
    )
    private List<Doctor> doctorList;

    @OneToMany
    @JoinColumn(name="patient_id")
    private List<Case> caseList;

}
