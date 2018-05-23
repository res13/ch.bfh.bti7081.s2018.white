package ch.bfh.bti7081.s2018.white.pms.ui.main;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import ch.bfh.bti7081.s2018.white.pms.ui.app.diary.DiaryOverview;
import ch.bfh.bti7081.s2018.white.pms.ui.app.goaltracker.GoaltrackerOverview;
import ch.bfh.bti7081.s2018.white.pms.ui.profile.ProfileView;
import ch.bfh.bti7081.s2018.white.pms.ui.settings.SettingsView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


import java.util.HashMap;

public abstract class DashboardView extends PmsSecureView {

    public static final String NAME = "pms";

    private Panel contentPanel;

    private HashMap<String, PmsSecureView> viewsMap = new HashMap<>();

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

    public abstract DiaryOverview getDiaryOverview();

    public abstract ProfileView getProfileOverview();

    public abstract GoaltrackerOverview getGoaltrackerOverview();

    public abstract SettingsView getSettingsView();

    public DashboardView() {
        super();
        setCaption("Dashboard");
        GoaltrackerOverview goaltrackerOverview = getGoaltrackerOverview();
        DiaryOverview diaryOverview = getDiaryOverview();
        ProfileView profileView = getProfileOverview();
        SettingsView settingsView = getSettingsView();
        viewsMap.put(goaltrackerOverview.NAME, goaltrackerOverview);
        viewsMap.put(diaryOverview.NAME, diaryOverview);
        viewsMap.put(ProfileView.NAME, profileView);
        viewsMap.put(SettingsView.NAME, settingsView);

        Label menuTitle = new Label("Menu");
        menuTitle.addStyleName(ValoTheme.MENU_TITLE);

        HorizontalLayout horizontalBody = new HorizontalLayout();
        VerticalLayout menuContent = new VerticalLayout();
        menuContent.addStyleName(ValoTheme.MENU_ROOT);

        Button btnGoal = new Button(MessageHandler.GOAL_TRACKER_NAME, new ButtonListener(GoaltrackerOverview.NAME));
        btnGoal.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);
        Button btnDiary = new Button(MessageHandler.DIARY_NAME, new ButtonListener(DiaryOverview.NAME));
        btnDiary.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);
        Button btnProfile = new Button(MessageHandler.PROFILE, new ButtonListener(ProfileView.NAME));
        btnProfile.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);
        Button btnLogout = new Button(MessageHandler.LOGOUT, clickEvent -> {
            VaadinSession.getCurrent().setAttribute(User.class, null);
            VaadinSession.getCurrent().close();
            UI.getCurrent().getNavigator().navigateTo(LoginView.NAME);
        });
        btnLogout.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);

        menuContent.addComponent(menuTitle);
        menuContent.addComponent(btnGoal);
        menuContent.addComponent(btnDiary);
        menuContent.addComponent(btnProfile);
        menuContent.addComponent(btnLogout);

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
            VaadinSession.getCurrent().close();
            return;
        }
        horizontalMenu.addComponent(new Button(MessageHandler.LOGOUT, clickEvent -> {
            VaadinSession.getCurrent().setAttribute(User.class, null);
            VaadinSession.getCurrent().close();
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
