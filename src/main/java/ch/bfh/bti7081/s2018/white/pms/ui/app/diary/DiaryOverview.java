package ch.bfh.bti7081.s2018.white.pms.ui.app.diary;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry;
import ch.bfh.bti7081.s2018.white.pms.ui.common.CustomButton;
import ch.bfh.bti7081.s2018.white.pms.ui.main.PmsSecureView;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TabSheet;

import java.util.List;

public abstract class DiaryOverview extends PmsSecureView {

    private Accordion accordion;
    private GridLayout gridDiary;
    private CustomButton newButton;

    @Override
    public void initialize() {
        accordion = new Accordion();
        gridDiary = new GridLayout(2, 1);
        newButton = new CustomButton(CustomButton.TypeEnum.NEW_DIARY_ENTRY);
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

    public void deleteDiaryEntry(TabSheet.Tab tab) {
        accordion.removeTab(tab);
    }

    private TabSheet.Tab addDiary(DiaryEntry diaryEntry) {
        String title = diaryEntry.getTitle();
        if (title == null) {
            title = MessageHandler.NEW_DIARY_ENTRY;
        }
        DiaryEntryView view = new DiaryEntryView(diaryEntry, this);
        TabSheet.Tab newDiaryEntryTab = accordion.addTab(view, title);
        view.setTab(newDiaryEntryTab);
        return newDiaryEntryTab;
    }


}
