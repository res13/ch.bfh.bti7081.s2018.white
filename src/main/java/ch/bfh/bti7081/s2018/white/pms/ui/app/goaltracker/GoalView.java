package ch.bfh.bti7081.s2018.white.pms.ui.app.goaltracker;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.Goal;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.GoalState;
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

import java.util.List;

public class GoalView extends VerticalLayout {

	private List<Patient> patients;
	private List<Goal> goals;
	private RelativeServiceImpl relativeServiceImpl;
	private GoalServiceImpl goalServiceImpl;
    private TextField firstName;
    private TextField lastName;
    private TextField email;
    private DateField startdate;
    private DateField enddate; 
    private TextField target; 
    private NativeSelect<GoalState> status;
    private DateField birthdate;
    private Button save;
    private Button delete;
    private ComboBox patientDropdown;  
    private Goal goal;
    private GoaltrackerOverview overview;
    private Binder<Goal> binder = new Binder<>(Goal.class);
    private User user;

    public GoalView(GoaltrackerOverview Dashboard) {
    	user = VaadinSession.getCurrent().getAttribute(User.class);
    	this.initialize();
    	overview = Dashboard;
       
        this.createView();
    }
    
    private void initialize() {
    	relativeServiceImpl = new RelativeServiceImpl(); 
    	goalServiceImpl = new GoalServiceImpl();
    	firstName = new TextField(MessageHandler.SURNAME);
        lastName = new TextField(MessageHandler.NAME);
        email = new TextField(MessageHandler.EMAIL);
        status = new NativeSelect<>(MessageHandler.STATUS);
        startdate = new DateField(MessageHandler.STARTDATE);
        enddate = new DateField(MessageHandler.ENDDATE);
        target = new TextField(MessageHandler.TARGET);
        save = new Button(MessageHandler.SAVE);
        delete = new Button(MessageHandler.DELETE);
        binder = new Binder<>(Goal.class);
        patientDropdown = new ComboBox();
    }
    
    private void createView() {
    	 setSizeUndefined();
         HorizontalLayout buttons = new HorizontalLayout(save, delete);
         addComponents(firstName, lastName, email, status, startdate, enddate, buttons);
         status.setItems(GoalState.values());
         save.setStyleName(ValoTheme.BUTTON_PRIMARY);
         save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
         save.addClickListener(e -> this.save());
         delete.addClickListener(e -> this.delete());

         if(user instanceof Relative){
             Relative relative = (Relative) this.user;
             patients = relative.getPatientList();
         }
        patientDropdown.setItems(patients);
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
        binder.setBean(goal);
        delete.setVisible(true);
        setVisible(true);
        firstName.selectAll();
    }

    private void delete() {
//        service.delete(goal);
        overview.updateList();
        setVisible(false);
    }

    private void save() {
//        service.save(goal);
        overview.updateList();
        setVisible(false);
    }
}