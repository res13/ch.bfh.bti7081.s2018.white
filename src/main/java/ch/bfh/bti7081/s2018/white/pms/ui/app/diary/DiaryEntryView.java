package ch.bfh.bti7081.s2018.white.pms.ui.app.diary;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.Comment;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import ch.bfh.bti7081.s2018.white.pms.services.impl.CommentServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.services.impl.DiaryEntryServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.ui.common.Notifier;
import ch.bfh.bti7081.s2018.white.pms.ui.main.PmsSecureView;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DiaryEntryView extends PmsSecureView {

    private DiaryEntryServiceImpl diaryEntryService = new DiaryEntryServiceImpl();
    private CommentServiceImpl commentService = new CommentServiceImpl();

    private GridLayout layout = new GridLayout(5, 5);
    private TextField title = new TextField();
    private TextArea text = new TextArea();
    private Label creator = new Label();
    private Label time = new Label();
    private DiaryEntry diaryEntry;
    private Button editButton = new Button("Edit");
    private Button saveButton = new Button("Save");


    public DiaryEntryView(DiaryEntry diaryEntry) {
        editButton.addClickListener(clickEvent -> switchEditable());
        saveButton.addClickListener(clickEvent -> saveDiaryEntry());

        try {
            List<Comment> diaryEntryEntities = commentService.getEntitiesByDiaryEntityId(diaryEntry.getId());

            Accordion accordion = new Accordion();
            for (Comment comment : diaryEntryEntities) {
                accordion.addTab(new CommentView(comment), "comment");
            }
            layout.addComponent(accordion, 0, 4);

        } catch (Exception e) {
            e.printStackTrace();
        }


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

        layout.addComponent(title, 0, 0);
        layout.addComponent(time, 0, 1);
        layout.addComponent(creator, 1, 1);
        layout.addComponent(text, 0, 2);

        addComponents(layout);
    }

    private void switchEditable() {
        title.setEnabled(!title.isEnabled());
        text.setEnabled(!text.isEnabled());

        layout.removeComponent(3, 3);
        if (this.diaryEntry != null && !title.isEnabled()) {
            layout.addComponent(editButton, 3, 3);
        } else {
            layout.addComponent(saveButton, 3, 3);
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
        Notifier.notify("Saved", "saved Entity");
//        Page.getCurrent().reload();
    }
}
