package ch.bfh.bti7081.s2018.white.pms.ui.app.diary;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry;
import ch.bfh.bti7081.s2018.white.pms.services.impl.DiaryEntryServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.services.impl.DiaryServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.ui.main.PmsSecureView;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TabSheet;

import java.util.HashMap;
import java.util.List;

public abstract class DiaryOverview extends PmsSecureView {

    private Accordion accordion;
    private HashMap<Long, TabSheet.Tab> diaryEntryToTab;
    private GridLayout gridDiary;
    private Button newButton;

    @Override
    public void initialize() {
        accordion = new Accordion();
        diaryEntryToTab = new HashMap<>();
        gridDiary = new GridLayout(2, 1);
        newButton = new Button("+");
    }

    public abstract List<DiaryEntry> getDiaryEntries();

    public abstract boolean addNewButton();

    @Override
    public void createView() {
        newButton.addClickListener(clickEvent -> newDiaryEntry());
        gridDiary.addComponent(accordion, 0, 0);
        if (addNewButton()) {
            gridDiary.addComponent(newButton, 1, 0);
        }
        addComponent(gridDiary);
        for (DiaryEntry diaryEntry : getDiaryEntries()) {
            addDiary(diaryEntry);
        }
    }

    private void newDiaryEntry() {
        TabSheet.Tab newDiaryEntryTab = addDiary(new DiaryEntry());
        accordion.setSelectedTab(newDiaryEntryTab);
    }

    public void deleteDiaryEntry(long diaryEntryId) {
        if (diaryEntryToTab.containsKey(diaryEntryId)) {
            accordion.removeTab(diaryEntryToTab.get(diaryEntryId));
            diaryEntryToTab.remove(diaryEntryId);
        }
    }

    private TabSheet.Tab addDiary(DiaryEntry diaryEntry) {
        String title = diaryEntry.getTitle();
        if (title == null) {
            title = "New";
        }
        TabSheet.Tab newDiaryEntryTab = accordion.addTab(new DiaryEntryView(diaryEntry, this), title);
        diaryEntryToTab.put(diaryEntry.getId(), newDiaryEntryTab);
        return newDiaryEntryTab;
    }

}
