package ch.bfh.bti7081.s2018.white.pms.common.model.app.diary;

import ch.bfh.bti7081.s2018.white.pms.common.model.PmsType;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
public class DiaryEntry extends PmsType {

    private LocalDateTime time;

    private String title;

    private String entryText;

    @OneToOne
    private User creator;

    private boolean relativeRead;

    private boolean patientRead;

    @ManyToOne
    private Diary diary;

    public DiaryEntry() {
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEntryText() {
        return entryText;
    }

    public void setEntryText(String entryText) {
        this.entryText = entryText;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public boolean isRelativeRead() {
        return relativeRead;
    }

    public void setRelativeRead(boolean relativeRead) {
        this.relativeRead = relativeRead;
    }

    public boolean isPatientRead() {
        return patientRead;
    }

    public void setPatientRead(boolean patientRead) {
        this.patientRead = patientRead;
    }
}
