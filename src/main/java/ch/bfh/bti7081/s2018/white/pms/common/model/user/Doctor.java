package ch.bfh.bti7081.s2018.white.pms.common.model.user;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Doctor extends User {

    @ManyToMany(mappedBy = "doctorList")
    private List<Patient> patientList;

    public Doctor() {
    }

    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }
}
