package ch.bfh.bti7081.s2018.white.pms.common.model.app.diary;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.App;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Diary extends App {

    @OneToMany
    private List<DiaryEntry> entryList;

    public Diary() {
//        name = MessageHandler.DIARY_NAME;
    }

    public List<DiaryEntry> getEntryList() {
        return entryList;
    }

    public void setEntryList(List<DiaryEntry> entryList) {
        this.entryList = entryList;
    }
}
