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
public class Caze extends PmsType {

    @ManyToOne
    private Patient patient;

    private LocalDateTime fromDate;

    private LocalDateTime toDate;

    private String note;

    @OneToMany
    @JoinColumn(name="caze_id")
    private List<App> appList;

    public Caze() {
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime from) {
        this.fromDate = from;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateTime to) {
        this.toDate = to;
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
