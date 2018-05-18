package ch.bfh.bti7081.s2018.white.pms.ui.app.diary;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry;
import ch.bfh.bti7081.s2018.white.pms.services.impl.DiaryEntryServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.ui.main.PmsSecureView;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.TabSheet;

import java.util.HashMap;
import java.util.List;

public class DiaryOverview extends PmsSecureView {

    private DiaryEntryServiceImpl diaryEntryService = new DiaryEntryServiceImpl();
    private Accordion accordion = new Accordion();
    private HashMap<Long, TabSheet.Tab> diaryEntryToTab = new HashMap<>();

    public static final String NAME = "diary";

    public DiaryOverview() {
        super();
        setCaption(NAME);
        GridLayout layout = new GridLayout(2, 1);

        try {
            List<DiaryEntry> allEntities = diaryEntryService.getAllEntities();

            for (DiaryEntry diaryEntry : allEntities) {
            	add(diaryEntry);    
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        layout.addComponent(accordion, 0, 0);
        Button newButton = new Button("+");
        newButton.addClickListener(clickEvent -> newDiaryEntry());
        layout.addComponent(newButton, 1, 0);
        addComponent(layout);

    }

    private void newDiaryEntry() {
        TabSheet.Tab newDiaryEntryTab = add(new DiaryEntry());
        accordion.setSelectedTab(newDiaryEntryTab);
    }
    
    public void deleteDiaryEntry(long diaryEntryId) {
    	if(diaryEntryToTab.containsKey(diaryEntryId)){
    		accordion.removeTab(diaryEntryToTab.get(diaryEntryId));
    		diaryEntryToTab.remove(diaryEntryId);
    	}
    }
    
    private TabSheet.Tab add(DiaryEntry diaryEntry){
    	String title = diaryEntry.getTitle();
    	if(title == null){
    		title = "New";	
    	}
    	TabSheet.Tab newDiaryEntryTab = accordion.addTab(new DiaryEntryView(diaryEntry, this), title);
    	diaryEntryToTab.put(diaryEntry.getId(), newDiaryEntryTab);
    	return newDiaryEntryTab;
    }

}
