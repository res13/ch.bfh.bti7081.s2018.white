package ch.bfh.bti7081.s2018.white.pms.ui.main;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import ch.bfh.bti7081.s2018.white.pms.ui.app.diary.DiaryOverview;
import ch.bfh.bti7081.s2018.white.pms.ui.app.diary.PatientDiaryOverview;
import ch.bfh.bti7081.s2018.white.pms.ui.app.diary.RelativeDiaryOverview;
import ch.bfh.bti7081.s2018.white.pms.ui.app.goaltracker.GoaltrackerOverview;
import ch.bfh.bti7081.s2018.white.pms.ui.profile.ProfileView;
import ch.bfh.bti7081.s2018.white.pms.ui.settings.SettingsView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


import java.util.HashMap;

public class DashboardView<T extends User> extends PmsSecureView<T> {

    public static final String NAME = "pms";

    private Panel contentPanel;

    private HashMap<String, PmsSecureView> viewsMap ;

    class ButtonListener implements Button.ClickListener {

        String viewName;
        public ButtonListener(String viewName) {
            this.viewName = viewName;
        }

        @Override
        public void buttonClick(Button.ClickEvent event) {
            UI.getCurrent().getNavigator().navigateTo(NAME + "/" + viewName);
        }
    }

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
        GoaltrackerOverview goaltrackerOverview = new GoaltrackerOverview();
        DiaryOverview patientDiaryOverview = new PatientDiaryOverview();
        DiaryOverview relativeDiaryOverview = new RelativeDiaryOverview();
        ProfileView profileView = new ProfileView();
        SettingsView settingsView = new SettingsView();
        viewsMap.put(goaltrackerOverview.NAME, goaltrackerOverview);
        viewsMap.put(PatientDiaryOverview.NAME, patientDiaryOverview);
        viewsMap.put(RelativeDiaryOverview.NAME, relativeDiaryOverview);
        viewsMap.put(ProfileView.NAME, profileView);
        viewsMap.put(SettingsView.NAME, settingsView);

        Label menuTitle = new Label("Menu");
        menuTitle.addStyleName(ValoTheme.MENU_TITLE);

        HorizontalLayout horizontalBody = new HorizontalLayout();
        VerticalLayout menuContent = new VerticalLayout();
        menuContent.addStyleName(ValoTheme.MENU_ROOT);

        Button btnGoal = new Button(MessageHandler.GOAL_TRACKER_NAME, new ButtonListener(GoaltrackerOverview.NAME));
        btnGoal.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);
        Button btnPatientDiary = new Button(MessageHandler.PATIENT_DIARY, new ButtonListener(PatientDiaryOverview.NAME));
        btnPatientDiary.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);
        Button btnRelativeDiary = new Button(MessageHandler.RELATIVE_DIARY, new ButtonListener(RelativeDiaryOverview.NAME));
        btnRelativeDiary.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);
        Button btnProfile = new Button(MessageHandler.PROFILE, new ButtonListener(ProfileView.NAME));
        btnProfile.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);

        menuContent.addComponent(menuTitle);
        menuContent.addComponent(btnGoal);
        menuContent.addComponent(btnPatientDiary);
        menuContent.addComponent(btnRelativeDiary);
        menuContent.addComponent(btnProfile);

        contentPanel = new Panel();
        horizontalBody.addComponents(menuContent, contentPanel);

        HorizontalLayout horizontalMenu = new HorizontalLayout();
        horizontalMenu.addStyleName(ValoTheme.MENU_ROOT);
        horizontalMenu.setSizeFull();
        User user = VaadinSession.getCurrent().getAttribute(User.class);

        if (user != null) {
            Label lblUsername = new Label(user.getName());
            horizontalMenu.addComponent(lblUsername);
            horizontalMenu.setComponentAlignment(lblUsername, Alignment.TOP_RIGHT);
        }
        else {
            UI.getCurrent().getNavigator().navigateTo(LoginView.NAME);
            return;
        }
        horizontalMenu.addComponent(new Button(MessageHandler.LOGOUT, clickEvent -> {
            VaadinSession.getCurrent().setAttribute(User.class, null);
            UI.getCurrent().getNavigator().navigateTo(LoginView.NAME);
        }));

        Button btnSetting = new Button(MessageHandler.SETTINGS, new ButtonListener(SettingsView.NAME));

        horizontalMenu.addComponent(btnSetting);
        horizontalMenu.setComponentAlignment(btnSetting, Alignment.TOP_RIGHT);


        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponents(horizontalMenu, horizontalBody);

        addComponent(verticalLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (event.getParameters() == null || event.getParameters().isEmpty()) {
            return;
        }
        else {
            if (viewsMap.containsKey(event.getParameters())) {
                contentPanel.setContent(viewsMap.get(event.getParameters()));
            }
        }
    }
}
