package ch.bfh.bti7081.s2018.white.pms.ui.app.diary;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.Comment;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import ch.bfh.bti7081.s2018.white.pms.services.impl.CommentServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.services.impl.DiaryEntryServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.ui.common.Notifier;
import ch.bfh.bti7081.s2018.white.pms.ui.main.PmsSecureView;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DiaryEntryView extends PmsSecureView {

    private DiaryEntryServiceImpl diaryEntryService = new DiaryEntryServiceImpl();
    private CommentServiceImpl commentService = new CommentServiceImpl();

    private VerticalLayout vLayout = new VerticalLayout();
    private HorizontalLayout hLayoutComments = new HorizontalLayout();
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


    public DiaryEntryView(DiaryEntry diaryEntry) {
        editButton.addClickListener(clickEvent -> switchEditable());
        saveButton.addClickListener(clickEvent -> saveDiaryEntry());
        newButton.addClickListener(clickEvent -> newComment());
        deleteButton.addClickListener(clickEvent -> deleteDiaryEntry());

        this.diaryEntry = diaryEntry;
        switchEditable();

        if (this.diaryEntry != null) {
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
        vLayout.addComponent(gLayout);
        
        if (this.diaryEntry != null) {
	        try {
	            List<Comment> diaryEntryEntities = commentService.getEntitiesByDiaryEntityId(diaryEntry.getId());
	            if (!diaryEntryEntities.isEmpty()) newButton.setCaption("+");
	            
	            for (Comment comment : diaryEntryEntities) {
	                accordionComments.addTab(new CommentView(comment), "comment");
	            }
	            
	            hLayoutComments.addComponent(accordionComments);
	            hLayoutComments.addComponent(newButton);
                hLayoutComments.addComponent(deleteButton);
	            vLayout.addComponent(hLayoutComments);
	
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
        }

        addComponents(vLayout);
    }

    private void deleteDiaryEntry() {
        System.out.println(this.diaryEntry.getTitle());
        try {
            diaryEntryService.deleteEntity(diaryEntry);
            Notifier.notify("Delete", "delete Entity "+diaryEntry.getTitle()+" with id "+diaryEntry.getId());
            Page.getCurrent().reload();
        } catch (Exception e) {
            e.printStackTrace();
            Notifier.notify("Error", "Not possible to delete Entity " + diaryEntry.getTitle() + " with id " + diaryEntry.getId());
        }
    }


    private void switchEditable() {
        title.setEnabled(!title.isEnabled());
        text.setEnabled(!text.isEnabled());

        gLayout.removeComponent(3, 3);
        if (this.diaryEntry != null && !title.isEnabled()) {
        	gLayout.addComponent(editButton, 3, 3);
        } else {
        	gLayout.addComponent(saveButton, 3, 3);
        }
    }


    private void saveDiaryEntry() {
        if (this.diaryEntry == null) {
            this.diaryEntry = new DiaryEntry();
        }

        diaryEntry.setTitle(title.getValue());
        diaryEntry.setEntryText(text.getValue());
        diaryEntry.setCreator(VaadinSession.getCurrent().getAttribute(User.class));
        diaryEntry.setTime(LocalDateTime.now());

        try {
            diaryEntryService.saveOrUpdateEntity(diaryEntry);
        } catch (Exception e) {
            e.printStackTrace();
        }

        switchEditable();
        Notifier.notify("Saved", "saved Entity "+diaryEntry.getTitle()+" with id "+diaryEntry.getId());
//        Page.getCurrent().reload();
    }
    
    private void newComment() {
        Comment comment = new Comment();
        comment.setDiaryEntry(this.diaryEntry);
        TabSheet.Tab newCommentTab = accordionComments.addTab(new CommentView(comment), "comment");
        accordionComments.setSelectedTab(newCommentTab);
        newButton.setCaption("+");
    }
}
