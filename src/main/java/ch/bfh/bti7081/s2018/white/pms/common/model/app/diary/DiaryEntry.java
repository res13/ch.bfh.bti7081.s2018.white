package ch.bfh.bti7081.s2018.white.pms.common.model.app.diary;

import ch.bfh.bti7081.s2018.white.pms.common.model.PmsEntity;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class DiaryEntry extends PmsEntity {

    private LocalDateTime time;

    private String title;

    private String entryText;

    @OneToOne
    private User creator;

    private boolean relativeRead;

    private boolean patientRead;

    @OneToMany(mappedBy = "diaryEntry", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> commentList;

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

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
