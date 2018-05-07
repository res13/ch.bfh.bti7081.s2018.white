package ch.bfh.bti7081.s2018.white.pms.ui.main;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.ui.PmsSecureView;
import ch.bfh.bti7081.s2018.white.pms.ui.app.AppsView;
import ch.bfh.bti7081.s2018.white.pms.ui.profile.ProfileView;
import ch.bfh.bti7081.s2018.white.pms.ui.settings.SettingsView;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

import java.util.HashMap;

public class DashboardView extends PmsSecureView {

    public static final String NAME = "pms";

    private VerticalLayout menuContent;

    private Panel contentPanel;

    private HashMap<String, PmsSecureView> viewsMap = new HashMap<>();

    class ButtonListener implements Button.ClickListener {

        String viewName;
        public ButtonListener(String viewName) {
            this.viewName = viewName;
        }

        @Override
        public void buttonClick(Button.ClickEvent event) {
            getUI().getNavigator().navigateTo(NAME + "/" + viewName);
        }
    }

    public DashboardView() {
        super();
        AppsView appsView = new AppsView();
        ProfileView profileView = new ProfileView();
        SettingsView settingsView = new SettingsView();
        viewsMap.put(AppsView.NAME, new AppsView());
        viewsMap.put(ProfileView.NAME, new ProfileView());
        viewsMap.put(SettingsView.NAME, new SettingsView());

        Panel panel = new Panel();
        panel.setSizeUndefined();
        addComponent(panel);
        menuContent = new VerticalLayout();
        menuContent.addComponent(new Button(MessageHandler.APPS, new ButtonListener(AppsView.NAME)));
        menuContent.addComponent(new Button(MessageHandler.PROFILE, new ButtonListener(ProfileView.NAME)));
        menuContent.addComponent(new Button(MessageHandler.SETTINGS, new ButtonListener(SettingsView.NAME)));
        menuContent.addComponent(new Button(MessageHandler.LOGOUT, clickEvent -> getUI().getNavigator().navigateTo(LoginView.NAME)));
        panel.setContent(menuContent);
        contentPanel = new Panel();
        // TODO: add contentPanel
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
