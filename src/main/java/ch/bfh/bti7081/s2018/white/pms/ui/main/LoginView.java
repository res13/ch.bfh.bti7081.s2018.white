package ch.bfh.bti7081.s2018.white.pms.ui.main;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Doctor;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Patient;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Relative;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import ch.bfh.bti7081.s2018.white.pms.services.UserService;
import ch.bfh.bti7081.s2018.white.pms.services.impl.UserServiceImpl;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.print.Doc;

public class LoginView extends VerticalLayout implements View {

    public static final Logger log = LogManager.getLogger(LoginView.class.getName());

    public static final String NAME = "";

    public LoginView(){
        setCaption(NAME);
        Panel panel = new Panel();
        panel.setSizeUndefined();
        addComponent(panel);

        FormLayout content = new FormLayout();
        TextField emailField = new TextField(MessageHandler.EMAIL);
        content.addComponent(emailField);
        PasswordField passwordField = new PasswordField(MessageHandler.PASSWORD);
        content.addComponent(passwordField);

        Button send = new Button(MessageHandler.LOGIN);
        send.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        send.addClickListener((Button.ClickListener) event -> {
            UserService userService = new UserServiceImpl();
            try {
                User user = userService.authenticate(emailField.getValue(), passwordField.getValue());
                VaadinSession.getCurrent().setAttribute(User.class, user);
                Class<? extends DashboardView> dashboardView;
                if (user instanceof Doctor) {
                    dashboardView = DashboardDoctorView.class;
                }
                else if (user instanceof Patient) {
                    dashboardView = DashboardPatientView.class;
                }
                else if (user instanceof Relative) {
                    dashboardView = DashboardRelativeView.class;
                }
                else {
                    throw new Exception("Invalid user");
                }
                UI.getCurrent().getNavigator().addView(DashboardView.NAME, dashboardView);
                UI.getCurrent().getNavigator().navigateTo(DashboardView.NAME);
            } catch (Exception e) {
                log.error(e);
                Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
            }
        });
        content.addComponent(send);
        content.setSizeUndefined();
        content.setMargin(true);
        panel.setContent(content);
        setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
