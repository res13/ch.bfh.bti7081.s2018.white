package ch.bfh.bti7081.s2018.white.pms.common.model.app.diary;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.App;

import java.util.List;

public class Diary extends App {

    private List<DiaryEntry> entryList;

    public Diary() {
        name = MessageHandler.DIARY_NAME;
    }

}
