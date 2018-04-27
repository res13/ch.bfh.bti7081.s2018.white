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

}
