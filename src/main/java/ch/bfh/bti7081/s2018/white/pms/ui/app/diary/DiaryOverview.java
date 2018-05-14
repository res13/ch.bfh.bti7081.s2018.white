package ch.bfh.bti7081.s2018.white.pms.ui.app.diary;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry;
import ch.bfh.bti7081.s2018.white.pms.services.impl.DiaryEntryServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.ui.main.PmsSecureView;
import com.vaadin.ui.Accordion;

import java.util.List;

public class DiaryOverview extends PmsSecureView {

    private DiaryEntryServiceImpl diaryEntryService = new DiaryEntryServiceImpl();

    public static final String NAME = "diary";

    public DiaryOverview() {
        super();
        setCaption(NAME);
        try {
            List<DiaryEntry> allEntities = diaryEntryService.getAllEntities();

            Accordion accordion = new Accordion();
            for (DiaryEntry diaryEntry : allEntities) {
                accordion.addTab(new DiaryEntryView(diaryEntry), diaryEntry.getTitle());
            }
            addComponent(accordion);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
