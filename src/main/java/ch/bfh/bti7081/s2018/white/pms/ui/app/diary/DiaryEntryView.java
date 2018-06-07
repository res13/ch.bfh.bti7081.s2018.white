package ch.bfh.bti7081.s2018.white.pms.ui.app.diary;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.Comment;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.Diary;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Patient;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Relative;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import ch.bfh.bti7081.s2018.white.pms.services.impl.CommentServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.services.impl.DiaryEntryServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.services.impl.DiaryServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.ui.common.CustomButton;
import ch.bfh.bti7081.s2018.white.pms.ui.common.Notifier;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.TabSheet.Tab;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DiaryEntryView extends VerticalLayout {

    private DiaryEntryServiceImpl diaryEntryService;
    private DiaryServiceImpl diaryService;
    private CommentServiceImpl commentService;
    private VerticalLayout vLayout;
    private CheckBox patientRead;
    private CheckBox relativeRead;
    private ComboBox<Patient> patientSelect;
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
    private CustomButton editButton;
    private CustomButton saveButton;
    private CustomButton newButton;
    private CustomButton deleteButton;
    private DiaryOverview parentDiary;
	private Tab tab;

    public DiaryEntryView(DiaryEntry diaryEntry, DiaryOverview diaryOverview) {
        initialize();
        this.diaryEntry = diaryEntry;
        this.parentDiary = diaryOverview;
        createView();
    }

    public void initialize() {
        diaryEntryService = new DiaryEntryServiceImpl();
        diaryService = new DiaryServiceImpl();
        commentService = new CommentServiceImpl();
        vLayout = new VerticalLayout();
        hLayoutComments = new HorizontalLayout();
        hLayoutPermissions = new HorizontalLayout();
        hLayoutButtons = new HorizontalLayout();
        patientSelect = new ComboBox<>(MessageHandler.PATIENT);
        patientSelect.setEmptySelectionAllowed(false);
        patientSelect.setTextInputAllowed(false);
        patientSelect.setItemCaptionGenerator(Patient::getFullName);
        gLayout = new GridLayout(4, 4);
        title = new TextField();
        text = new TextArea();
        creator = new Label();
        time = new Label();
        patientRead = new CheckBox(MessageHandler.PATIENT_READ);
        relativeRead = new CheckBox(MessageHandler.RELATIVE_READ);
        accordionComments = new Accordion();
        editButton = new CustomButton(CustomButton.typeEnum.EDIT);
        saveButton = new CustomButton(CustomButton.typeEnum.SAVE);
        deleteButton = new CustomButton(CustomButton.typeEnum.DELETE);
        newButton = new CustomButton(CustomButton.typeEnum.NEW_COMMENT);
    }

    public void createView() {
        User user = VaadinSession.getCurrent().getAttribute(User.class);
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
            if (user instanceof Relative) {
                patientSelect.setItems(diaryEntry.getDiary().getCaze().getPatient());
                patientSelect.setSelectedItem(diaryEntry.getDiary().getCaze().getPatient());
            }
        } else {
            creator.setValue(VaadinSession.getCurrent().getAttribute(User.class).getFullName());
            time.setValue(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            if (user instanceof Relative) {
                patientSelect.setItems(((Relative) user).getPatientList());
            }
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
                List<Comment> diaryEntryEntities = commentService.getEntitiesByDiaryEntity(diaryEntry);
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
            if (tab != null) {
            	parentDiary.deleteDiaryEntry(tab);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void switchEditable() {
        title.setEnabled(!title.isEnabled());
        text.setEnabled(!text.isEnabled());
        patientRead.setEnabled(!patientRead.isEnabled());
        patientSelect.setEnabled(!patientSelect.isEnabled());
        
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
                    hLayoutPermissions.addComponent(patientSelect);
                }
            }
        } else {
            hLayoutButtons.addComponent(saveButton);
            if (user instanceof Patient && parentDiary instanceof PatientDiaryOverview) {
                hLayoutPermissions.addComponent(relativeRead);
            } else if (user instanceof Relative && parentDiary instanceof RelativeDiaryOverview) {
                hLayoutPermissions.addComponent(patientRead);
                hLayoutPermissions.addComponent(patientSelect);
            }
        }
    }

    private void saveDiaryEntry() {
        if (this.diaryEntry.getId() == null) {
            this.diaryEntry = new DiaryEntry();
        }
        if(title.getValue().isEmpty() == true){
        	Notifier.notify(MessageHandler.NOT_SAVED, MessageHandler.NOT_SAVED_DIARY_ENTRY);
    		title.focus();
    		return;
    	} else if (text.getValue().isEmpty() == true){
    		Notifier.notify(MessageHandler.NOT_SAVED, MessageHandler.NOT_SAVED_DIARY_ENTRY);
    		text.focus();
    		return;
    	}
        User user = VaadinSession.getCurrent().getAttribute(User.class);
        diaryEntry.setTitle(title.getValue());
        diaryEntry.setEntryText(text.getValue());
        diaryEntry.setCreator(user);
        diaryEntry.setTime(LocalDateTime.now());
        Long selectedPatientId = null;
        if (user instanceof Relative) {
        	if(patientSelect.getSelectedItem().isPresent() == true){
        		selectedPatientId = patientSelect.getSelectedItem().get().getId();
        	} else {
        		Notifier.notify(MessageHandler.NOT_SAVED, MessageHandler.NOT_SAVED_DIARY_ENTRY);
        		patientSelect.focus();
        		return;
        	}          
        }
        else if (user instanceof Patient) {
            selectedPatientId = user.getId();
        }
        if (selectedPatientId != null) {
            try {
                Diary diary = diaryService.getDiaryByPatientEntityId(selectedPatientId);
                diaryEntry.setDiary(diary);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } 
        if (user instanceof Patient) {
            diaryEntry.setRelativeRead(relativeRead.getValue());
        } else if (user instanceof Relative) {
            diaryEntry.setPatientRead(patientRead.getValue());
        } 
          
        try {
            this.diaryEntry = diaryEntryService.saveOrUpdateEntity(diaryEntry);
            tab.setCaption(diaryEntry.getTitle());
            switchEditable();
            Notifier.notify(MessageHandler.SAVED, MessageHandler.SAVED_DIARY_ENTRY);
        } catch (Exception e) {
            e.printStackTrace();
        }      
    }

    private void newComment() {
        Comment comment = new Comment();
        comment.setDiaryEntry(this.diaryEntry);
        TabSheet.Tab newCommentTab = addComment(comment);
        accordionComments.setSelectedTab(newCommentTab);
    }

    private TabSheet.Tab addComment(Comment comment) {
    	CommentView view = new CommentView(comment, this);
        TabSheet.Tab newCommentTab = accordionComments.addTab(view, "");
        view.setTab(newCommentTab);
        return newCommentTab;
    }

    public void deleteComment(TabSheet.Tab tab) {
            accordionComments.removeTab(tab);
    }

	public void setTab(Tab newDiaryEntryTab) {
		this.tab = newDiaryEntryTab;
	}
}
