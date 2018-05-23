package ch.bfh.bti7081.s2018.white.pms.ui.app.diary;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.App;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.Diary;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry;
import ch.bfh.bti7081.s2018.white.pms.common.model.caze.Caze;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Patient;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Relative;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import ch.bfh.bti7081.s2018.white.pms.services.impl.DiaryEntryServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.services.impl.DiaryServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.ui.main.PmsSecureView;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

import java.util.HashMap;
import java.util.List;

public abstract class DiaryOverview extends PmsSecureView {

    private DiaryEntryServiceImpl diaryEntryService = new DiaryEntryServiceImpl();
    private DiaryServiceImpl diaryService = new DiaryServiceImpl();
    private Accordion accordionRelative = new Accordion();
    private Label labelRelativeDiary = new Label(MessageHandler.RELATIVE_DIARY);
    private Label labelPatientDiary = new Label("Patiententagebuch");
    private HashMap<Long, TabSheet.Tab> diaryEntryToTab = new HashMap<>();
    private VerticalLayout layout = new VerticalLayout();
    private GridLayout gridRelativeDiary = new GridLayout(2, 1);
    private Button newButtonRelative = new Button("+");
    private Accordion accordionPatients = new Accordion();

    public static final String NAME = "diary";

    public DiaryOverview() {
        super();
        setCaption(NAME);
        User user = VaadinSession.getCurrent().getAttribute(User.class);

        if(user instanceof Relative){
	        try {
	            List<DiaryEntry> relativeEntities = diaryEntryService.getEntitiesByCreatorId(user.getId());
	            for (DiaryEntry diaryEntry : relativeEntities) {
	            	addRelativeDiary(diaryEntry);    
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
        
	        newButtonRelative.addClickListener(clickEvent -> newRelativeDiaryEntry());
	        gridRelativeDiary.addComponent(accordionRelative, 0, 0);       
	        gridRelativeDiary.addComponent(newButtonRelative, 1, 0);
	        layout.addComponent(labelRelativeDiary);
	        layout.addComponent(gridRelativeDiary);
	        layout.addComponent(labelPatientDiary);
	        
	        try {
	        		List<Patient> relativePatients = ((Relative) user).getPatientList();
	        	
		        	for (Patient patient : relativePatients) {
		            	Accordion accordionPatient = new Accordion();
		            	accordionPatients.addTab(accordionPatient,patient.getFullName());
		               
		                List<Caze> patientCazes = patient.getCazeList();
		                for (Caze caze : patientCazes) {
		                	Accordion accordionCaze = new Accordion();
		                	accordionPatient.addTab(accordionPatient,caze.getNote());
		                	List<App> cazeApps = caze.getAppList();
		                	for (App app : cazeApps) {
		                		Accordion accordionDiary = new Accordion();
		                    	Diary diary;
		                    	                  
		                        GridLayout gridPatientDiary = new GridLayout(2, 1);
		                        Button newButtonPatient = new Button("+");
		
		                        
		    					try {
		    						diary = diaryService.getEntityById(app.getId());
		    						newButtonPatient.addClickListener(clickEvent -> newPatientDiaryEntry(accordionDiary,diary.getId()));
		    								
		    			            gridPatientDiary.addComponent(accordionDiary, 0, 0);       
		    			            gridPatientDiary.addComponent(newButtonPatient, 1, 0);
		    			            accordionCaze.addTab(gridPatientDiary,diary.getName());
		
		    			            
		    						List<DiaryEntry> patientEntities = diaryEntryService.getEntitiesByDiaryId(diary.getId());
		    			            for (DiaryEntry diaryEntry : patientEntities) {
		    			            	addPatientDiary(accordionDiary,diaryEntry);    
		    			            }
		
		    						
		    	                    layout.addComponent(gridPatientDiary);
		    					} catch (Exception e) {
		    						e.printStackTrace();
		    					}   	
		                    }
		                }      
		            }
	        } catch (Exception e) {
				e.printStackTrace();
			} 
    	}
        
        addComponent(layout);

    }

    private void newRelativeDiaryEntry() {
        TabSheet.Tab newDiaryEntryTab = addRelativeDiary(new DiaryEntry());
        accordionRelative.setSelectedTab(newDiaryEntryTab);
    }
    
    private void newPatientDiaryEntry(Accordion accordionDiary,long diaryId) {
        TabSheet.Tab newDiaryEntryTab = addRelativeDiary(new DiaryEntry());
        accordionDiary.setSelectedTab(newDiaryEntryTab);
    }
    
    public void deleteDiaryEntry(long diaryEntryId) {
    	if(diaryEntryToTab.containsKey(diaryEntryId)){
    		accordionRelative.removeTab(diaryEntryToTab.get(diaryEntryId));
    		diaryEntryToTab.remove(diaryEntryId);
    	}
    }
    
    private TabSheet.Tab addRelativeDiary(DiaryEntry diaryEntry){
    	String title = diaryEntry.getTitle();
    	if(title == null){
    		title = "New";	
    	}
    	TabSheet.Tab newDiaryEntryTab = accordionRelative.addTab(new DiaryEntryView(diaryEntry, this), title);
    	diaryEntryToTab.put(diaryEntry.getId(), newDiaryEntryTab);
    	return newDiaryEntryTab;
    }
    
    private TabSheet.Tab addPatientDiary(Accordion accordionDiary, DiaryEntry diaryEntry){
    	String title = diaryEntry.getTitle();
    	if(title == null){
    		title = "New";	
    	}
    	TabSheet.Tab newDiaryEntryTab = accordionDiary.addTab(new DiaryEntryView(diaryEntry, this), title);
    	diaryEntryToTab.put(diaryEntry.getId(), newDiaryEntryTab);
    	return newDiaryEntryTab;
    }

}
