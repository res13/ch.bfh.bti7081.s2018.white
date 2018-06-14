package ch.bfh.bti7081.s2018.white.pms.ui.main;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Doctor;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Patient;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Relative;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import ch.bfh.bti7081.s2018.white.pms.ui.app.diary.DiaryOverview;
import ch.bfh.bti7081.s2018.white.pms.ui.app.diary.PatientDiaryOverview;
import ch.bfh.bti7081.s2018.white.pms.ui.app.diary.RelativeDiaryOverview;
import ch.bfh.bti7081.s2018.white.pms.ui.app.goaltracker.GoaltrackerOverview;
import ch.bfh.bti7081.s2018.white.pms.ui.profile.ProfileView;
import ch.bfh.bti7081.s2018.white.pms.ui.settings.SettingsView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.HashMap;

public class DashboardView extends PmsSecureView {

    public static final String NAME = "pms";

    private Panel contentPanel;

    private HashMap<String, PmsSecureView> viewsMap;

    private User user;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void initialize() {
        viewsMap = new HashMap<>();
    }

    @Override
    public void createView() {
        user = VaadinSession.getCurrent().getAttribute(User.class);
        if (user == null) {
            UI.getCurrent().getNavigator().navigateTo(LoginView.NAME);
            return;
        }

        GoaltrackerOverview goaltrackerOverview = new GoaltrackerOverview();
        DiaryOverview patientDiaryOverview = new PatientDiaryOverview();
        DiaryOverview relativeDiaryOverview = new RelativeDiaryOverview();
        ProfileView profileView = new ProfileView();
        SettingsView settingsView = new SettingsView();
        viewsMap.put(DashboardView.NAME, this);
        viewsMap.put(goaltrackerOverview.NAME, goaltrackerOverview);
        viewsMap.put(PatientDiaryOverview.NAME, patientDiaryOverview);
        viewsMap.put(RelativeDiaryOverview.NAME, relativeDiaryOverview);
        viewsMap.put(ProfileView.NAME, profileView);
        viewsMap.put(SettingsView.NAME, settingsView);

        HorizontalLayout horizontalBody = new HorizontalLayout();
        VerticalLayout menuContent = new VerticalLayout();
        menuContent.addStyleName(ValoTheme.MENU_ROOT);

        Button btnDashboard = new Button(MessageHandler.DASHBOARD_NAME, new ButtonListener(DashboardView.NAME));
        btnDashboard.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);
        Button btnGoal = new Button(MessageHandler.GOAL_TRACKER_NAME, new ButtonListener(GoaltrackerOverview.NAME));
        btnGoal.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);
        Button btnPatientDiary = new Button(MessageHandler.PATIENT_DIARY, new ButtonListener(PatientDiaryOverview.NAME));
        btnPatientDiary.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);
        Button btnRelativeDiary = new Button(MessageHandler.RELATIVE_DIARY, new ButtonListener(RelativeDiaryOverview.NAME));
        btnRelativeDiary.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);

        menuContent.addComponent(btnDashboard);
        menuContent.addComponent(btnGoal);
        menuContent.addComponent(btnPatientDiary);
        menuContent.addComponent(btnRelativeDiary);
        menuContent.addStyleName("myMenu");

        contentPanel = new Panel();
        contentPanel.setContent(getDashboardViewForRole());
        contentPanel.setSizeFull();
        horizontalBody.addComponents(menuContent, contentPanel);

        HorizontalLayout horizontalMenu = new HorizontalLayout();
        horizontalMenu.addStyleName(ValoTheme.MENU_ROOT);
        horizontalMenu.setSizeFull();

        HorizontalLayout topRightGroup = new HorizontalLayout();

        Label lblUsername = new Label(user.getFullName());
        topRightGroup.addComponent(lblUsername);

        Button btnProfile = new Button("", new ButtonListener(ProfileView.NAME));
        ThemeResource profileImage = new ThemeResource("images/profile.png");
        btnProfile.setIcon(profileImage);
        btnProfile.setStyleName(ValoTheme.BUTTON_LINK);
        btnProfile.addStyleName("btnProfile");
        topRightGroup.addComponent(btnProfile);

        Button btnLogout = new Button("", clickEvent -> {
            VaadinSession.getCurrent().setAttribute(User.class, null);
            UI.getCurrent().getNavigator().navigateTo(LoginView.NAME);
        });
        ThemeResource logoutImage = new ThemeResource("images/logout.png");
        btnLogout.setIcon(logoutImage);
        btnLogout.setStyleName(ValoTheme.BUTTON_LINK);
        btnLogout.addStyleName("btnProfile");
        topRightGroup.addComponent(btnLogout);

        horizontalMenu.addComponent(topRightGroup);
        horizontalMenu.setComponentAlignment(topRightGroup, Alignment.MIDDLE_RIGHT);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponents(horizontalMenu, horizontalBody);
        verticalLayout.addStyleName("vlMain");
        addStyleName("vlMain");
        addComponent(verticalLayout);
    }

    private GridLayout getDashboardViewForRole() {
        GridLayout gridLayout = null;
        if (user instanceof Relative) {
            gridLayout = new RelativeDashboardView((Relative) this.user).getGridLayout();
        }
        return gridLayout;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (event.getParameters() == null || event.getParameters().isEmpty()) {
            return;
        } else if (event.getParameters().equals(NAME)) {
            contentPanel.setContent(getDashboardViewForRole());
        } else if (viewsMap.containsKey(event.getParameters())) {
            contentPanel.setContent(viewsMap.get(event.getParameters()));
        }
    }

    static class ButtonListener implements Button.ClickListener {

        String viewName;

        public ButtonListener(String viewName) {
            this.viewName = viewName;
        }

        @Override
        public void buttonClick(Button.ClickEvent event) {
            UI.getCurrent().getNavigator().navigateTo(NAME + "/" + viewName);
        }
    }
}
