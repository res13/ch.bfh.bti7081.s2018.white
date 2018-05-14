package ch.bfh.bti7081.s2018.white.pms.ui.app.diary;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry;
import ch.bfh.bti7081.s2018.white.pms.ui.main.PmsSecureView;
import com.vaadin.ui.*;

public class DiaryEntryView  extends PmsSecureView {

    private TextField title = new TextField();
    private TextArea text = new TextArea();
    private TextField creator = new TextField();
    private DateTimeField time = new DateTimeField();


    public DiaryEntryView(DiaryEntry diaryEntry) {
        GridLayout layout = new GridLayout(4, 4);

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
