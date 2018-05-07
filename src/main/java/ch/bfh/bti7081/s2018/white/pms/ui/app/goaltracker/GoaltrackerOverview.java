package ch.bfh.bti7081.s2018.white.pms.ui.app.goaltracker;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.goaltracker.Goal;
import ch.bfh.bti7081.s2018.white.pms.services.impl.GoalServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.ui.main.PmsSecureView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import javax.servlet.annotation.WebServlet;
import java.time.LocalDateTime;
import java.util.List;

@Theme("mytheme")
public class GoaltrackerOverview extends PmsSecureView {

    private GoalServiceImpl goalService = new GoalServiceImpl();
    private Grid<Goal> grid = new Grid<>(Goal.class);
    private TextField filterText = new TextField();
    private GoalView form = new GoalView(this);
    public static final String NAME = "goaltracker";

    public GoaltrackerOverview() {
        super();
        setCaption(NAME);
        Goal goal = new Goal();
        goal.setGoal("abc");
        goal.setCreated(LocalDateTime.now());

        try {
            goalService.saveOrUpdateEntity(goal);
        } catch (Exception e) {
            e.printStackTrace();
        }


        final VerticalLayout layout = new VerticalLayout();

        filterText.setPlaceholder("filter by name...");
        filterText.addValueChangeListener(e -> updateList());
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        Button clearFilterTextBtn = new Button(FontAwesome.TIMES);
        clearFilterTextBtn.setDescription("Clear the current filter");
        clearFilterTextBtn.addClickListener(e -> filterText.clear());

        CssLayout filtering = new CssLayout();
        filtering.addComponents(filterText, clearFilterTextBtn);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        Button addCustomerBtn = new Button("Add new customer");
        addCustomerBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setGoal(new Goal());
        });

        HorizontalLayout toolbar = new HorizontalLayout(filtering, addCustomerBtn);

        grid.setColumns("goal", "goal", "goal");

        HorizontalLayout main = new HorizontalLayout(grid, form);
        main.setSizeFull();
        grid.setSizeFull();
        main.setExpandRatio(grid, 1);

        layout.addComponents(toolbar, main);

        // fetch list of Customers from goalService and assign it to Grid
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
        List<Goal> goals = null;
        try {
            goals = goalService.getAllEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        grid.setItems(goals);
    }
}
