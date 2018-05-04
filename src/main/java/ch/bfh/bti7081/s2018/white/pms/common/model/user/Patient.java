package ch.bfh.bti7081.s2018.white.pms.common.model.user;

import ch.bfh.bti7081.s2018.white.pms.common.model.caze.Caze;

import javax.persistence.*;
import java.util.List;

@Entity
public class Patient extends User {

    @ManyToMany
    @JoinTable(name = "Patient_Relative",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "relative_id")
    )
    private List<Relative> relativeList;

    @ManyToMany
    @JoinTable(name = "Patient_Doctor",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    private List<Doctor> doctorList;

    @OneToMany
    @JoinColumn(name = "patient_id")
    private List<Caze> cazeList;

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

    public List<Caze> getCazeList() {
        return cazeList;
    }

    public void setCazeList(List<Caze> cazeList) {
        this.cazeList = cazeList;
    }


}
