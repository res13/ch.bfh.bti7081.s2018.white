package ch.bfh.bti7081.s2018.white.pms.ui.app.goaltracker;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.Goal;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import ch.bfh.bti7081.s2018.white.pms.services.impl.GoalServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.ui.common.CustomButton;
import ch.bfh.bti7081.s2018.white.pms.ui.main.PmsSecureView;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

public class GoaltrackerOverview extends PmsSecureView {

    public static final String NAME = "goaltracker";
    private GoalServiceImpl goalService;
    private Grid<Goal> grid;
    private TextField filterText;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void initialize() {
        goalService = new GoalServiceImpl();
        grid = new Grid<>(Goal.class);
        filterText = new TextField();
    }

    @Override
    public void createView() {
        final VerticalLayout layout = new VerticalLayout();
        filterText.setPlaceholder(MessageHandler.FILTER);
        filterText.addValueChangeListener(e -> updateList());
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        Button clearFilterTextBtn = new Button(FontAwesome.TIMES);
        clearFilterTextBtn.setDescription(MessageHandler.CLEAR_FILTER);
        clearFilterTextBtn.addClickListener(e -> filterText.clear());
        CssLayout filtering = new CssLayout();
        filtering.addComponents(filterText, clearFilterTextBtn);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        CustomButton addCustomerBtn = new CustomButton(CustomButton.TypeEnum.NEW_GOAL);
        GoalView form = new GoalView(this);
        addCustomerBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setGoal(new Goal());
        });
        HorizontalLayout toolbar = new HorizontalLayout(filtering, addCustomerBtn);
        grid.setColumns("goal");
        HorizontalLayout main = new HorizontalLayout(grid, form);
        main.setExpandRatio(grid, 1);
        layout.addComponents(toolbar, main);
        layout.addStyleName("myLayout");
        updateList();
        addComponent(layout);
        form.setVisible(false);
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() == null) {
                form.setVisible(false);
            } else {
                form.setGoal(event.getValue());
            }
        });
    }

    public void updateList() {
        User user = (User) VaadinSession.getCurrent().getAttribute(User.class.getName());
        List<Goal> goals = null;
        try {
            goals = goalService.getGoalEntriesForUserAndFilter(user, filterText.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        grid.setItems(goals);

    }
}
