package ch.bfh.bti7081.s2018.white.pms.ui.main;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import javax.servlet.annotation.WebServlet;

@Theme("mytheme")
public class NavigatorUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        new Navigator(this, this);
        getNavigator().addView(LoginView.NAME, LoginView.class);
        setupMessageProvider(vaadinRequest);
    }

    private void setupMessageProvider(VaadinRequest request) {
        request.getService().setSystemMessagesProvider(systemMessagesInfo -> {
            CustomizedSystemMessages msgs = new CustomizedSystemMessages();
            msgs.setSessionExpiredMessage(MessageHandler.PLEASE_LOGIN_AGAIN);
            msgs.setSessionExpiredCaption(MessageHandler.SESSION_CLOSED);
            msgs.setSessionExpiredNotificationEnabled(true);
            msgs.setSessionExpiredURL("http://localhost:8080");
            return msgs;
        });
    }

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = NavigatorUI.class)
    public static class Servlet extends VaadinServlet {
    }

}
