package ch.bfh.bti7081.s2018.white.pms.common.model.user;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table
public class Relative extends User {

    @ManyToMany

    private List<Patient> patientList;

    public Relative() {
    }

    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }
}