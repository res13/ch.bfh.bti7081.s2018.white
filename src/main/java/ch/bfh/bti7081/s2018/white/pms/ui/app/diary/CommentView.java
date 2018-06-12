package ch.bfh.bti7081.s2018.white.pms.ui.app.diary;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.Comment;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import ch.bfh.bti7081.s2018.white.pms.services.impl.CommentServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.ui.common.CustomButton;
import ch.bfh.bti7081.s2018.white.pms.ui.common.Notifier;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.*;
import com.vaadin.ui.TabSheet.Tab;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommentView extends VerticalLayout implements View {

    private CommentServiceImpl commentService = new CommentServiceImpl();

    private GridLayout gLayout = new GridLayout(2,3);
    private HorizontalLayout hLayoutButtons = new HorizontalLayout();
    private TextArea text = new TextArea();
    private TextField creator = new TextField();
    private DateTimeField time = new DateTimeField();
    private Comment comment;
    private CustomButton editButton;
    private CustomButton saveButton;
    private CustomButton deleteButton;
    private DiaryEntryView parentView;
	private Tab tab;


    public CommentView(Comment comment, DiaryEntryView diaryEntryView) {
        this.comment = comment;
        this.parentView = diaryEntryView;
        text.setWidth(100,Unit.PERCENTAGE);

        editButton = new CustomButton(CustomButton.typeEnum.EDIT);
        saveButton = new CustomButton(CustomButton.typeEnum.SAVE);
        deleteButton = new CustomButton(CustomButton.typeEnum.DELETE);
        
        editButton.addClickListener(clickEvent -> switchEditable());
        saveButton.addClickListener(clickEvent -> saveComment());
        deleteButton.addClickListener(clickEvent -> deleteComment());

        switchEditable();

        if (this.comment.getId() != null) {
            text.setValue(comment.getCommentText());
            creator.setValue(comment.getCreator().getFullName());
            time.setValue(comment.getTime());
        } else {
            creator.setValue(VaadinSession.getCurrent().getAttribute(User.class).getFullName());
            time.setValue(LocalDateTime.now());
            switchEditable();
        }

        creator.setEnabled(false);
        time.setEnabled(false);

        gLayout.addComponent(time, 0, 0);
        gLayout.addComponent(creator, 1, 0);
        gLayout.addComponent(text, 0, 1, 1, 1);
        gLayout.addComponent(hLayoutButtons, 0, 2);
        gLayout.setSpacing(true);
        addComponents(gLayout);
    }

    private void switchEditable() {
        User userSession = VaadinSession.getCurrent().getAttribute(User.class);
        text.setEnabled(!text.isEnabled());

        hLayoutButtons.removeAllComponents();
        if (this.comment.getId() != null && !text.isEnabled()) {
            if (userSession.getId() != null) {
                if (userSession.getId() == comment.getCreator().getId()) {
                    hLayoutButtons.addComponent(editButton);
                    hLayoutButtons.addComponent(deleteButton);
                }
            }
        } else {
            hLayoutButtons.addComponent(saveButton);
        }
    }


    private void saveComment() {
        comment.setCommentText(text.getValue());
        comment.setCreator(VaadinSession.getCurrent().getAttribute(User.class));
        comment.setTime(LocalDateTime.now());

    	if (text.getValue().isEmpty() == true){
    		Notifier.notify(MessageHandler.NOT_SAVED, MessageHandler.NOT_SAVED_COMMENT);
    		text.focus();
    		return;
    	}
        
        try {
            comment = commentService.saveOrUpdateEntity(comment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        switchEditable();
        Notifier.notify(MessageHandler.SAVED, MessageHandler.SAVED_COMMENT);
    }

    private void deleteComment() {
        try {
            commentService.deleteEntity(comment);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Notifier.notify(MessageHandler.DELETED, MessageHandler.DELETED_COMMENT);
        if (tab != null) {
        	parentView.deleteComment(tab);
        }
    }
    
	public void setTab(Tab newCommentTab) {
		this.tab = newCommentTab;
	}
}
