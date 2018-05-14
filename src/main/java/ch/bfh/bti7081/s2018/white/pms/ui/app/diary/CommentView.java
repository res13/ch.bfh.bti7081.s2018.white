package ch.bfh.bti7081.s2018.white.pms.ui.app.diary;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.Comment;
import ch.bfh.bti7081.s2018.white.pms.ui.main.PmsSecureView;

import com.vaadin.ui.*;

public class CommentView  extends PmsSecureView {

    private TextArea text = new TextArea();
    private TextField creator = new TextField();
    private DateTimeField time = new DateTimeField();


    public CommentView(Comment comment) {
        GridLayout layout = new GridLayout(2, 3);

        text.setValue(comment.getCommentText());
        creator.setValue(comment.getCreator().getFullName());
        time.setValue(comment.getTime());
        //Button editButton= new Button("Edit");
        //editButton.addClickListener(clickEvent -> switchEditable());

        layout.addComponent(time, 0, 1);
        layout.addComponent(creator, 1, 1);
        layout.addComponent(text, 0, 2);
        
        switchEditable();

        addComponents(layout);
    }

    private void switchEditable() {
        text.setEnabled(!text.isEnabled());
        creator.setEnabled(!creator.isEnabled());
        time.setEnabled(!time.isTextFieldEnabled());
    }
}
