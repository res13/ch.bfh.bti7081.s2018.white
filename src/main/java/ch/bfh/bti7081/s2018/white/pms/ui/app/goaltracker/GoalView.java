package ch.bfh.bti7081.s2018.white.pms.ui.app.goaltracker;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.Goal;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.GoalState;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.GoalTracker;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Doctor;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Patient;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Relative;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import ch.bfh.bti7081.s2018.white.pms.services.impl.GoalServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.services.impl.GoalTrackerServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.ui.common.ButtonType;
import ch.bfh.bti7081.s2018.white.pms.ui.common.CustomButton;
import ch.bfh.bti7081.s2018.white.pms.ui.common.Notifier;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GoalView extends VerticalLayout {

    private List<Patient> patients;
    private GoalTrackerServiceImpl goalTrackerServiceImpl;
    private GoalServiceImpl goalServiceImpl;
    private DateTimeField createdOn;
    private DateTimeField endDate;
    private TextField creator;
    private TextArea goalText;
    private NativeSelect<GoalState> status;
    private CustomButton save;
    private CustomButton delete;
    private ComboBox<Patient> patientDropdown;
    private Goal goal;
    private GoaltrackerOverview overview;
    private Binder<Goal> binder = new Binder<>(Goal.class);
    private User user;

    public GoalView(GoaltrackerOverview Dashboard) {
        user = VaadinSession.getCurrent().getAttribute(User.class);
        overview = Dashboard;
        this.initialize();
        this.createView();
    }

    private void initialize() {
        goalServiceImpl = new GoalServiceImpl();
        goalTrackerServiceImpl = new GoalTrackerServiceImpl();
        status = new NativeSelect<>(MessageHandler.STATUS);
        createdOn = new DateTimeField(MessageHandler.STARTDATE);
        endDate = new DateTimeField(MessageHandler.ENDDATE);
        creator = new TextField(MessageHandler.CREATED_BY);
        goalText = new TextArea(MessageHandler.GOAL);
        save = new CustomButton(ButtonType.SAVE);
        delete = new CustomButton(ButtonType.DELETE);
        binder = new Binder<>(Goal.class);
        patientDropdown = new ComboBox(MessageHandler.PATIENT);
        patients = new ArrayList<>();
    }

    private void createView() {
        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        addComponents(patientDropdown, creator, createdOn, endDate, status, goalText, buttons);
        endDate.setRangeStart(LocalDateTime.now());
        endDate.setDateOutOfRangeMessage(MessageHandler.OUT_OF_RANGE);
        status.setEmptySelectionAllowed(false);
        status.setItems(GoalState.values());
        createdOn.setReadOnly(true);
        creator.setReadOnly(true);
        patientDropdown.setEmptySelectionAllowed(false);
        patientDropdown.setTextInputAllowed(false);
        patientDropdown.setItemCaptionGenerator(Patient::getFullName);
        binder.bind(createdOn, Goal::getCreated, Goal::setCreated);
        binder.bind(endDate, Goal::getDueTo, Goal::setDueTo);
        binder.bind(goalText, Goal::getGoal, Goal::setGoal);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addClickListener(e -> this.save());
        delete.addClickListener(e -> this.delete());
        patientDropdown.setItems(patients);
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
        binder.setBean(goal);
        if (goal.getId() != null) {
            patientDropdown.setItems(goal.getGoalTracker().getCaze().getPatient());
            patientDropdown.setSelectedItem(goal.getGoalTracker().getCaze().getPatient());
            status.setSelectedItem(goal.getState());
            creator.setValue(goal.getCreator().getFullName());
            delete.setVisible(true);
        } else {
            if (user instanceof Patient) {
                Patient patient = (Patient) user;
                patientDropdown.setItems(patient);
                patientDropdown.setSelectedItem(patient);
            } else if (user instanceof Relative) {
                Relative relative = (Relative) this.user;
                patients = relative.getPatientList();
                patientDropdown.setItems(patients);
                patientDropdown.setSelectedItem(patients.get(0));
            } else if (user instanceof Doctor) {
                Doctor doctor = (Doctor) this.user;
                patients = doctor.getPatientList();
                patientDropdown.setItems(patients);
                patientDropdown.setSelectedItem(patients.get(0));
            }
            creator.setValue(user.getFullName());
            status.setSelectedItem(GoalState.OPEN);
            goal.setCreated(LocalDateTime.now());
            createdOn.setValue(goal.getCreated());
            delete.setVisible(false);
        }
        setVisible(true);
    }

    private void delete() {
        try {
            goalServiceImpl.deleteEntity(goal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        overview.updateList();
        setVisible(false);
    }

    private void save() {
        if (patientDropdown.getSelectedItem().isPresent() != true) {
            Notifier.notify(MessageHandler.NOT_SAVED, MessageHandler.NOT_SAVED_GOAL);
            patientDropdown.focus();
            return;
        } else if (goalText.getValue().isEmpty() == true) {
            Notifier.notify(MessageHandler.NOT_SAVED, MessageHandler.NOT_SAVED_GOAL);
            goalText.focus();
            return;
        }
        try {
            //if (goal.getId() == null) {
            goal.setState(status.getValue());
            goal.setCreator(user);
            Long patientId = patientDropdown.getSelectedItem().get().getId();
            GoalTracker goalTracker = goalTrackerServiceImpl.getGoalTrackerByPatientEntityId(patientId);
            goal.setGoalTracker(goalTracker);
            //}
            goalServiceImpl.saveOrUpdateEntity(goal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        overview.updateList();
        Notifier.notify(MessageHandler.SAVED, MessageHandler.SAVED_GOAL);
    }
}