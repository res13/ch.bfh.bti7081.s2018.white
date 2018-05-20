package ch.bfh.bti7081.s2018.white.pms.ui.app.diary;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.Comment;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import ch.bfh.bti7081.s2018.white.pms.services.impl.CommentServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.services.impl.DiaryEntryServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.ui.common.Notifier;
import ch.bfh.bti7081.s2018.white.pms.ui.main.PmsSecureView;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class DiaryEntryView extends PmsSecureView {

    private DiaryEntryServiceImpl diaryEntryService = new DiaryEntryServiceImpl();
    private CommentServiceImpl commentService = new CommentServiceImpl();

    private VerticalLayout vLayout = new VerticalLayout();
    private HorizontalLayout hLayoutComments = new HorizontalLayout();
    private HorizontalLayout hLayoutButtons = new HorizontalLayout();
    private GridLayout gLayout = new GridLayout(4, 4);
    private TextField title = new TextField();
    private TextArea text = new TextArea();
    private Label creator = new Label();
    private Label time = new Label();
    private DiaryEntry diaryEntry;
    private Accordion accordionComments = new Accordion();
    private Button editButton = new Button("Edit");
    private Button saveButton = new Button("Save");
    private Button newButton = new Button("New comment");
    private Button deleteButton = new Button("Delete");
    private DiaryOverview parentDiary;
    private HashMap<Long, TabSheet.Tab> commentToTab = new HashMap<>();


    public DiaryEntryView(DiaryEntry diaryEntry, DiaryOverview diaryOverview) {
        editButton.addClickListener(clickEvent -> switchEditable());
        saveButton.addClickListener(clickEvent -> saveDiaryEntry());
        newButton.addClickListener(clickEvent -> newComment());
        deleteButton.addClickListener(clickEvent -> deleteDiaryEntry());

        this.parentDiary = diaryOverview;
        this.diaryEntry = diaryEntry;
        switchEditable();

        if (this.diaryEntry.getId() != null) {
            title.setValue(diaryEntry.getTitle());
            text.setValue(diaryEntry.getEntryText());
            creator.setValue(diaryEntry.getCreator().getFullName());
            time.setValue(diaryEntry.getTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        } else {
            creator.setValue(VaadinSession.getCurrent().getAttribute(User.class).getFullName());
            time.setValue(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            switchEditable();
        }

        //not editable fields
        creator.setEnabled(false);
        time.setEnabled(false);

        gLayout.addComponent(title, 0, 0);
        gLayout.addComponent(time, 0, 1);
        gLayout.addComponent(creator, 1, 1);
        gLayout.addComponent(text, 0, 2);
        gLayout.addComponent(hLayoutButtons, 3, 3);
        vLayout.addComponent(gLayout);
        
        if (this.diaryEntry.getId() != null) {
	        try {
	            List<Comment> diaryEntryEntities = commentService.getEntitiesByDiaryEntityId(diaryEntry.getId());
	            if (!diaryEntryEntities.isEmpty()) newButton.setCaption("+");
	            
	            for (Comment comment : diaryEntryEntities) {
	                addComment(comment);
	            }
	            
	            hLayoutComments.addComponent(accordionComments);
	            hLayoutComments.addComponent(newButton);
	            vLayout.addComponent(hLayoutComments);
	
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
        }

        addComponents(vLayout);
    }

    private void deleteDiaryEntry() {
        try {
            if(this.accordionComments.getComponentCount() > 0){
            	try {
    	            List<Comment> diaryEntryEntities = commentService.getEntitiesByDiaryEntityId(diaryEntry.getId());
    	            
    	            for (Comment comment : diaryEntryEntities) {
    	            	commentService.deleteEntity(comment);
    	            }
            	} catch (Exception e) {
     	            e.printStackTrace();
     	        }
            }
            diaryEntryService.deleteEntity(diaryEntry);
            Notifier.notify("Delete", "deleted Entity");
            parentDiary.deleteDiaryEntry(diaryEntry.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void switchEditable() {
        title.setEnabled(!title.isEnabled());
        text.setEnabled(!text.isEnabled());

        hLayoutButtons.removeAllComponents();;
        if (this.diaryEntry.getId() != null && !title.isEnabled()) {
        	if(VaadinSession.getCurrent().getAttribute(User.class) != null){
        		if(VaadinSession.getCurrent().getAttribute(User.class).getId() == diaryEntry.getCreator().getId()){
            	hLayoutButtons.addComponent(editButton);
            	hLayoutButtons.addComponent(deleteButton);
        		}
        	}
        } else {
        	hLayoutButtons.addComponent(saveButton);
        }
    }


    private void saveDiaryEntry() {
        if (this.diaryEntry.getId() == null) {
            this.diaryEntry = new DiaryEntry();
        }

        diaryEntry.setTitle(title.getValue());
        diaryEntry.setEntryText(text.getValue());
        diaryEntry.setCreator(VaadinSession.getCurrent().getAttribute(User.class));
        diaryEntry.setTime(LocalDateTime.now());

        try {
            this.diaryEntry = diaryEntryService.saveOrUpdateEntity(diaryEntry);
        } catch (Exception e) {
            e.printStackTrace();
        }

        switchEditable();
        Notifier.notify("Saved", "saved Entity");
    }
    
    private void newComment() {
    	Comment comment = new Comment();
    	comment.setDiaryEntry(this.diaryEntry);
        TabSheet.Tab newCommentTab = addComment(comment);
        accordionComments.setSelectedTab(newCommentTab);
        newButton.setCaption("+");
    }
    
    private TabSheet.Tab addComment(Comment comment) {
    	TabSheet.Tab newCommentTab = accordionComments.addTab(new CommentView(comment, this), "comment");
    	if(comment.getId() != null){
    		commentToTab.put(comment.getId(), newCommentTab);
    	}
    	System.out.println("Before update to DB: "+comment.getId());
    	return newCommentTab;
    }
    
    public void deleteComment(long CommentId) {
    	if(commentToTab.containsKey(CommentId)){
    		accordionComments.removeTab(commentToTab.get(CommentId));
    		commentToTab.remove(CommentId);
    		if(this.accordionComments.getComponentCount() == 0){
    			newButton.setCaption("New comment");
    		}
    	}
    }
}
