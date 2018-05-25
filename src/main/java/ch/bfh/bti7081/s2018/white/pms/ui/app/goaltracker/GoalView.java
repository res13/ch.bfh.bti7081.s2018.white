package ch.bfh.bti7081.s2018.white.pms.ui.app.goaltracker;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.Goal;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.GoalState;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class GoalView extends VerticalLayout {

    private TextField firstName = new TextField(MessageHandler.SURNAME);
    private TextField lastName = new TextField(MessageHandler.NAME);
    private TextField email = new TextField(MessageHandler.EMAIL);
    private NativeSelect<GoalState> status = new NativeSelect<>(MessageHandler.STATUS);
    private DateField birthdate = new DateField(MessageHandler.BIRTHDAY);
    private Button save = new Button(MessageHandler.SAVE);
    private Button delete = new Button(MessageHandler.DELETE);

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
        save.addClickListener(e -> this.save());
        delete.addClickListener(e -> this.delete());
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