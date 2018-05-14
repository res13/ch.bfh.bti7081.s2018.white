package ch.bfh.bti7081.s2018.white.pms.ui.app.diary;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.Comment;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry;
import ch.bfh.bti7081.s2018.white.pms.services.impl.CommentServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.ui.main.PmsSecureView;

import java.util.List;

import com.vaadin.ui.*;

public class DiaryEntryView  extends PmsSecureView {

    private TextField title = new TextField();
    private TextArea text = new TextArea();
    private TextField creator = new TextField();
    private DateTimeField time = new DateTimeField();
    private CommentServiceImpl<Comment> commentService = new CommentServiceImpl<Comment>();


    public DiaryEntryView(DiaryEntry diaryEntry) {
        GridLayout layout = new GridLayout(5, 5);
        try {
			List<Comment> diaryEntryEntities = commentService.getEntitiesByDiaryEntityId(diaryEntry.getId());
			
			 Accordion accordion = new Accordion();
	            for (Comment comment : diaryEntryEntities) {
	                accordion.addTab(new CommentView(comment), "comment");
	            }
	            layout.addComponent(accordion, 4, 0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

        title.setValue(diaryEntry.getTitle());
        text.setValue(diaryEntry.getEntryText());
        creator.setValue(diaryEntry.getCreator().getFullName());
        time.setValue(diaryEntry.getTime());
        Button editButton= new Button("Edit");
        editButton.addClickListener(clickEvent -> switchEditable());

        layout.addComponent(title, 0, 0);
        layout.addComponent(time, 0, 1);
        layout.addComponent(creator, 1, 1);
        layout.addComponent(text, 0, 2);
        layout.addComponent(editButton, 3, 3);

        switchEditable();

        addComponents(layout);
    }

    private void switchEditable() {
        title.setEnabled(!title.isEnabled());
        text.setEnabled(!text.isEnabled());
        creator.setEnabled(!creator.isEnabled());
        time.setEnabled(!time.isTextFieldEnabled());
    }
}
