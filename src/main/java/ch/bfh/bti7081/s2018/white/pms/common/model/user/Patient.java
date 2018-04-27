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

    public Patient() {

    }

    public List<Relative> getRelativeList() {
        return relativeList;
    }

    public void setRelativeList(List<Relative> relativeList) {
        this.relativeList = relativeList;
    }

    public List<Doctor> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(List<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    public List<Case> getCaseList() {
        return caseList;
    }

    public void setCaseList(List<Case> caseList) {
        this.caseList = caseList;
    }


}
