package ch.bfh.bti7081.s2018.white.pms.ui.app.diary;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.Comment;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Patient;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Relative;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import ch.bfh.bti7081.s2018.white.pms.services.impl.CommentServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.services.impl.DiaryEntryServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.ui.common.Notifier;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class DiaryEntryView extends VerticalLayout {

    private DiaryEntryServiceImpl diaryEntryService;
    private CommentServiceImpl commentService;
    private VerticalLayout vLayout;
    private CheckBox patientRead;
    private CheckBox relativeRead;
    private HorizontalLayout hLayoutComments;
    private HorizontalLayout hLayoutPermissions;
    private HorizontalLayout hLayoutButtons;
    private GridLayout gLayout;
    private TextField title;
    private TextArea text;
    private Label creator;
    private Label time;
    private DiaryEntry diaryEntry;
    private Accordion accordionComments;
    private Button editButton;
    private Button saveButton;
    private Button newButton;
    private Button deleteButton;
    private DiaryOverview parentDiary;
    private HashMap<Long, TabSheet.Tab> commentToTab;

    public DiaryEntryView(DiaryEntry diaryEntry, DiaryOverview diaryOverview) {
        initialize();
        this.diaryEntry = diaryEntry;
        this.parentDiary = diaryOverview;
        createView();
    }

    public void initialize() {
        diaryEntryService = new DiaryEntryServiceImpl();
        commentService = new CommentServiceImpl();
        vLayout = new VerticalLayout();
        hLayoutComments = new HorizontalLayout();
        hLayoutPermissions = new HorizontalLayout();
        hLayoutButtons = new HorizontalLayout();
        gLayout = new GridLayout(4, 4);
        title = new TextField();
        text = new TextArea();
        creator = new Label();
        time = new Label();
        patientRead = new CheckBox(MessageHandler.PATIENT_READ);
        relativeRead = new CheckBox(MessageHandler.RELATIVE_READ);
        accordionComments = new Accordion();
        editButton = new Button(MessageHandler.EDIT);
        saveButton = new Button(MessageHandler.SAVE);
        newButton = new Button(MessageHandler.NEW_COMMENT);
        deleteButton = new Button(MessageHandler.DELETE);
        commentToTab = new HashMap<>();
    }

    public void createView() {
        editButton.addClickListener(clickEvent -> switchEditable());
        saveButton.addClickListener(clickEvent -> saveDiaryEntry());
        newButton.addClickListener(clickEvent -> newComment());
        deleteButton.addClickListener(clickEvent -> deleteDiaryEntry());
        switchEditable();
        if (this.diaryEntry.getId() != null) {
            title.setValue(diaryEntry.getTitle());
            text.setValue(diaryEntry.getEntryText());
            creator.setValue(diaryEntry.getCreator().getFullName());
            time.setValue(diaryEntry.getTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            relativeRead.setValue(diaryEntry.isRelativeRead());
            patientRead.setValue(diaryEntry.isPatientRead());
        } else {
            creator.setValue(VaadinSession.getCurrent().getAttribute(User.class).getFullName());
            time.setValue(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            switchEditable();
        }
        creator.setEnabled(false);
        time.setEnabled(false);
        gLayout.addComponent(title, 0, 0);
        gLayout.addComponent(time, 0, 1);
        gLayout.addComponent(creator, 1, 1);
        gLayout.addComponent(text, 0, 2);
        gLayout.addComponent(hLayoutPermissions, 0, 3);
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
            diaryEntryService.deleteEntity(diaryEntry);
            Notifier.notify(MessageHandler.DELETED, MessageHandler.DELETED_DIARY_ENTRY);
            parentDiary.deleteDiaryEntry(diaryEntry.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void switchEditable() {
        title.setEnabled(!title.isEnabled());
        text.setEnabled(!text.isEnabled());
        hLayoutButtons.removeAllComponents();
        hLayoutPermissions.removeAllComponents();
        User user = VaadinSession.getCurrent().getAttribute(User.class);
        if (this.diaryEntry.getId() != null && !title.isEnabled()) {
            if (user.getId() != null && user.getId() == diaryEntry.getCreator().getId()) {
                hLayoutButtons.addComponent(editButton);
                hLayoutButtons.addComponent(deleteButton);
                if (user instanceof Patient) {
                    hLayoutPermissions.addComponent(relativeRead);
                } else if (user instanceof Relative) {
                    hLayoutPermissions.addComponent(patientRead);
                }
            }
        } else {
            hLayoutButtons.addComponent(saveButton);
            if (user instanceof Patient && parentDiary instanceof PatientDiaryOverview) {
                hLayoutPermissions.addComponent(relativeRead);
            } else if (user instanceof Relative && parentDiary instanceof RelativeDiaryOverview) {
                hLayoutPermissions.addComponent(patientRead);
            }
        }
    }

    private void saveDiaryEntry() {
        if (this.diaryEntry.getId() == null) {
            this.diaryEntry = new DiaryEntry();
        }
        User user = VaadinSession.getCurrent().getAttribute(User.class);
        diaryEntry.setTitle(title.getValue());
        diaryEntry.setEntryText(text.getValue());
        diaryEntry.setCreator(user);
        diaryEntry.setTime(LocalDateTime.now());
        //TODO: Add new diaryEntry to a diary
        //diaryEntry.setDiary();
        if (user instanceof Patient) {
            diaryEntry.setRelativeRead(relativeRead.getValue());
        } else if (user instanceof Relative) {
            diaryEntry.setPatientRead(patientRead.getValue());
        }
        try {
            this.diaryEntry = diaryEntryService.saveOrUpdateEntity(diaryEntry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        switchEditable();
        Notifier.notify(MessageHandler.SAVED, MessageHandler.SAVED_DIARY_ENTRY);
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
        if (comment.getId() != null) {
            commentToTab.put(comment.getId(), newCommentTab);
        }
        return newCommentTab;
    }

    public void deleteComment(long CommentId) {
        if (commentToTab.containsKey(CommentId)) {
            accordionComments.removeTab(commentToTab.get(CommentId));
            commentToTab.remove(CommentId);
            if (this.accordionComments.getComponentCount() == 0) {
                newButton.setCaption("New comment");
            }
        }
    }
}
