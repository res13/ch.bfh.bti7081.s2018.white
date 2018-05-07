package ch.bfh.bti7081.s2018.white.pms.ui.main;

import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.VerticalLayout;

public abstract class PmsSecureView extends VerticalLayout implements View {

    public PmsSecureView() {
        checkLogin();
    }

    private void checkLogin() {
        User user = (User) VaadinSession.getCurrent().getAttribute(User.class.getName());
        if (user == null) {
            getUI().getNavigator().navigateTo(LoginView.NAME);
        }
    }
}
