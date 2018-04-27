package ch.bfh.bti7081.s2018.white.pms.common.model.app.diary;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.App;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Diary extends App {

    @OneToMany
    @JoinColumn(name="diary_id")
    private List<DiaryEntry> entryList;

    public Diary() {
        name = MessageHandler.DIARY_NAME;
    }

}
