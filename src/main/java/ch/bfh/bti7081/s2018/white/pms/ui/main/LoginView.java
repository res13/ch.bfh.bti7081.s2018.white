package ch.bfh.bti7081.s2018.white.pms.ui.main;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import ch.bfh.bti7081.s2018.white.pms.services.UserService;
import ch.bfh.bti7081.s2018.white.pms.services.impl.UserServiceImpl;
import ch.bfh.bti7081.s2018.white.pms.ui.common.ButtonType;
import ch.bfh.bti7081.s2018.white.pms.ui.common.CustomButton;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginView extends VerticalLayout implements View {

    public static final Logger log = LogManager.getLogger(LoginView.class.getName());

    public static final String NAME = "";

    public LoginView() {
        setCaption(NAME);
        createView();
    }

    public void createView() {
        Panel panel = new Panel();
        panel.setSizeUndefined();
        addComponent(panel);

        FormLayout content = new FormLayout();
        panel.addStyleName("myLogin");
        Label welcome = new Label(MessageHandler.WELCOME);
        welcome.addStyleName("myLabel");
        content.addComponent(welcome);
        TextField emailField = new TextField(MessageHandler.EMAIL);
        content.addComponent(emailField);
        PasswordField passwordField = new PasswordField(MessageHandler.PASSWORD);
        content.addComponent(passwordField);

        CustomButton send = new CustomButton(ButtonType.LOGIN);
        send.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        send.addClickListener((Button.ClickListener) event -> checkUserLogin(emailField.getValue(), passwordField.getValue()));
        content.addComponent(send);
        content.setSizeUndefined();
        content.setMargin(true);
        panel.setContent(content);
        setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
    }

    private void checkUserLogin(String email, String password) {
        UserService userService = new UserServiceImpl();
        try {
            User user = userService.authenticate(email, password);
            VaadinSession.getCurrent().setAttribute(User.class, user);
            UI.getCurrent().getNavigator().addView(DashboardView.NAME, DashboardView.class);
            UI.getCurrent().getNavigator().navigateTo(DashboardView.NAME);
        } catch (Exception e) {
            log.error(e);
            Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
    }

}
