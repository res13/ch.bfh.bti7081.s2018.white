package ch.bfh.bti7081.s2018.white.pms.common.model.caze;

import ch.bfh.bti7081.s2018.white.pms.common.model.PmsType;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.App;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Patient;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
public class Case extends PmsType {

    @ManyToOne
    private Patient patient;

    private LocalDateTime from;

    private LocalDateTime to;

    private String note;

    @OneToMany
    private List<App> appList;

    public Case() {
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<App> getAppList() {
        return appList;
    }

    public void setAppList(List<App> appList) {
        this.appList = appList;
    }
}
