package ch.bfh.bti7081.s2018.white.pms.ui.app.diary;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.Comment;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import ch.bfh.bti7081.s2018.white.pms.services.impl.CommentServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.ui.common.Notifier;
import ch.bfh.bti7081.s2018.white.pms.ui.main.PmsSecureView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;

public class CommentView  extends PmsSecureView {

	private CommentServiceImpl commentService = new CommentServiceImpl();
	
	private VerticalLayout vLayout = new VerticalLayout();
	private HorizontalLayout hLayoutInfos = new HorizontalLayout();
	private HorizontalLayout hLayoutButtons = new HorizontalLayout();
    private TextArea text = new TextArea();
    private Label creator = new Label();
    private Label time = new Label();
    private Comment comment;
    private Button editButton= new Button("Edit");
    private Button saveButton = new Button("Save");


    public CommentView(Comment comment) {
        this.comment = comment;
        
        editButton.addClickListener(clickEvent -> switchEditable());
        saveButton.addClickListener(clickEvent -> saveComment());
        
        switchEditable();

        if (this.comment != null) {
        	text.setValue(comment.getCommentText());
        	text.setSizeFull();
            creator.setValue(comment.getCreator().getFullName());
            time.setValue(comment.getTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        } else {
        	creator.setValue(VaadinSession.getCurrent().getAttribute(User.class).getFullName());
            time.setValue(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        	switchEditable();
        }
        
        creator.setEnabled(false);
        time.setEnabled(false);
        
        hLayoutInfos.addComponent(time);
        hLayoutInfos.addComponent(creator);
        vLayout.addComponent(hLayoutInfos);
	    vLayout.addComponent(text);
	    vLayout.addComponent(hLayoutButtons);
        addComponents(vLayout);
    }

    private void switchEditable() {
        text.setEnabled(!text.isEnabled());
        
        hLayoutButtons.removeAllComponents();
        if (this.comment != null && !text.isEnabled()) {
        	hLayoutButtons.addComponent(editButton);
        } else {
        	hLayoutButtons.addComponent(saveButton);
        }
    }
    
    
    private void saveComment() {
        if (this.comment == null) {
            this.comment = new Comment();
        }

        comment.setCommentText(text.getValue());
        comment.setCreator(VaadinSession.getCurrent().getAttribute(User.class));
        comment.setTime(LocalDateTime.now());

        try {
            commentService.saveOrUpdateEntity(comment);
        } catch (Exception e) {
            e.printStackTrace();
        }

        switchEditable();
        Notifier.notify("Saved", "saved comment "+comment.getCommentText()+" with id "+comment.getId());
    }
}
