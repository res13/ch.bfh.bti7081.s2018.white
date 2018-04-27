package ch.bfh.bti7081.s2018.white.pms.common.model.user;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "doctor")
public class Doctor extends User {

    @ManyToMany
    private List<Patient> patientList;
}
