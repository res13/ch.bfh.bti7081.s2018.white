package ch.bfh.bti7081.s2018.white.pms.ui.app.diary;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry;
import ch.bfh.bti7081.s2018.white.pms.services.impl.DiaryEntryServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.ui.main.PmsSecureView;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TabSheet;

import java.util.List;

public class DiaryOverview extends PmsSecureView {

    private DiaryEntryServiceImpl diaryEntryService = new DiaryEntryServiceImpl();
    private Accordion accordion = new Accordion();

    public static final String NAME = "diary";

    public DiaryOverview() {
        super();
        setCaption(NAME);
        GridLayout layout = new GridLayout(2, 1);

        try {
            List<DiaryEntry> allEntities = diaryEntryService.getAllEntities();

            for (DiaryEntry diaryEntry : allEntities) {
                accordion.addTab(new DiaryEntryView(diaryEntry), diaryEntry.getTitle());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        layout.addComponent(accordion, 0, 0);
        Button newButton = new Button();
        newButton.addClickListener(clickEvent -> newDiaryEntry());
        layout.addComponent(newButton, 1, 0);
        addComponent(layout);

    }

    private void newDiaryEntry() {
        TabSheet.Tab newDiaryEntryTab = accordion.addTab(new DiaryEntryView(null), "New");
        accordion.setSelectedTab(newDiaryEntryTab);
    }

}
