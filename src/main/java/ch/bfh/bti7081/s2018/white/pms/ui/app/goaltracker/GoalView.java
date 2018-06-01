package ch.bfh.bti7081.s2018.white.pms.ui.app.goaltracker;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.Goal;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.GoalState;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Doctor;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Patient;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Relative;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import ch.bfh.bti7081.s2018.white.pms.services.impl.GoalServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.services.impl.RelativeServiceImpl;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.List;

public class GoalView extends VerticalLayout {

	private List<Patient> patients;
	private List<Goal> goals;
	private RelativeServiceImpl relativeServiceImpl;
	private GoalServiceImpl goalServiceImpl;
    private DateTimeField createdOn;
    private DateTimeField endDate;
    private Label creator;
    private TextField goalText;
    private NativeSelect<GoalState> status;
    private Button save;
    private Button delete;
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
    	relativeServiceImpl = new RelativeServiceImpl(); 
    	goalServiceImpl = new GoalServiceImpl();
        status = new NativeSelect<>(MessageHandler.STATUS);
        createdOn = new DateTimeField(MessageHandler.STARTDATE);
        endDate = new DateTimeField(MessageHandler.ENDDATE);
        creator = new Label(MessageHandler.CREATED_BY);
        goalText = new TextField(MessageHandler.GOAL);
        save = new Button(MessageHandler.SAVE);
        delete = new Button(MessageHandler.DELETE);
        binder = new Binder<>(Goal.class);
        patientDropdown = new ComboBox(MessageHandler.PATIENT);
        patientDropdown.setEmptySelectionAllowed(false);
        patientDropdown.setTextInputAllowed(false);
        patientDropdown.setItemCaptionGenerator(Patient::getFullName);
        patients = new ArrayList<>();
    }
    
    private void createView() {
    	 setSizeUndefined();
         HorizontalLayout buttons = new HorizontalLayout(save, delete);
         addComponents(patientDropdown, creator, createdOn, endDate, status, goalText, buttons);
         status.setItems(GoalState.values());
         save.setStyleName(ValoTheme.BUTTON_PRIMARY);
         save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
         save.addClickListener(e -> this.save());
         delete.addClickListener(e -> this.delete());

         if (user instanceof Patient) {
             Patient patient = (Patient) user;
             patientDropdown.setItems(patient);
             patientDropdown.setSelectedItem(patient);
         }
         else if(user instanceof Relative){
             Relative relative = (Relative) this.user;
             patients = relative.getPatientList();
         }
         else if(user instanceof Doctor){
             Doctor doctor = (Doctor) this.user;
             patients = doctor.getPatientList();
         }
        patientDropdown.setItems(patients);
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
        binder.setBean(goal);
        delete.setVisible(true);
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
        try {
            goalServiceImpl.saveOrUpdateEntity(goal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        overview.updateList();
        setVisible(false);
    }
}