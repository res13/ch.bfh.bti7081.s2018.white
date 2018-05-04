package ch.bfh.bti7081.s2018.white.pms.ui.app.goaltracker;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.Goal;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.GoalState;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class GoalView extends FormLayout {

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField email = new TextField("Email");
    private NativeSelect<GoalState> status = new NativeSelect<>("Status");
    private DateField birthdate = new DateField("Birthday");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private Goal goal;
    private GoaltrackerOverview overview;
    private Binder<Goal> binder = new Binder<>(Goal.class);

    public GoalView(GoaltrackerOverview Dashboard) {
        this.overview = Dashboard;

        setSizeUndefined();
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        addComponents(firstName, lastName, email, status, birthdate, buttons);

        status.setItems(GoalState.values());
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

//        binder.bindInstanceFields(this);

        save.addClickListener(e -> this.save());
        delete.addClickListener(e -> this.delete());
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
        binder.setBean(goal);

        // Show delete button for only customers already in the database
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