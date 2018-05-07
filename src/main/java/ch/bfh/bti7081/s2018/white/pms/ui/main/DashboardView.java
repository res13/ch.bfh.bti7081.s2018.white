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

import java.util.HashMap;

public class DashboardView extends PmsSecureView {

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

    public DashboardView() {
        super();
        setCaption("Dashboard");
        GoaltrackerOverview goaltrackerOverview = new GoaltrackerOverview();
        DiaryOverview diaryOverview = new DiaryOverview();
        ProfileView profileView = new ProfileView();
        SettingsView settingsView = new SettingsView();
        viewsMap.put(goaltrackerOverview.NAME, new GoaltrackerOverview());
        viewsMap.put(diaryOverview.NAME, new DiaryOverview());
        viewsMap.put(ProfileView.NAME, new ProfileView());
        viewsMap.put(SettingsView.NAME, new SettingsView());

        HorizontalLayout horizontalBody = new HorizontalLayout();
        VerticalLayout menuContent = new VerticalLayout();
        menuContent.addComponent(new Button(MessageHandler.GOAL_TRACKER_NAME, new ButtonListener(GoaltrackerOverview.NAME)));
        menuContent.addComponent(new Button(MessageHandler.DIARY_NAME, new ButtonListener(DiaryOverview.NAME)));
        menuContent.addComponent(new Button(MessageHandler.PROFILE, new ButtonListener(ProfileView.NAME)));
        menuContent.addComponent(new Button(MessageHandler.SETTINGS, new ButtonListener(SettingsView.NAME)));
        contentPanel = new Panel();
        horizontalBody.addComponents(menuContent, contentPanel);

        HorizontalLayout horizontalMenu = new HorizontalLayout();
        User user = VaadinSession.getCurrent().getAttribute(User.class);
        if (user != null) {
            horizontalMenu.addComponent(new Label(user.getName()));
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
