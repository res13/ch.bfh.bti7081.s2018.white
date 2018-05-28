package ch.bfh.bti7081.s2018.white.pms.ui.profile;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Doctor;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Patient;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Relative;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import ch.bfh.bti7081.s2018.white.pms.ui.main.PmsSecureView;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

public class ProfileView extends PmsSecureView {

    public static final String NAME = "profile";
    private Label firstnameLabel;
    private Label lastnameLabel;
    private Label emailLabel;
    private Label roleLabel;
    private Label birthdayLabel;
    private Label firstnameValueLabel;
    private Label lastnameValueLabel;
    private Label emailValueLabel;
    private Label roleValueLabel;
    private Label birthdayValueLabel;
    private GridLayout layout;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void initialize() {
        firstnameLabel = new Label(MessageHandler.SURNAME);
        lastnameLabel = new Label(MessageHandler.NAME);
        emailLabel = new Label(MessageHandler.EMAIL);
        roleLabel = new Label(MessageHandler.STATUS);
        birthdayLabel = new Label(MessageHandler.BIRTHDAY);
        firstnameValueLabel = new Label();
        lastnameValueLabel = new Label();
        emailValueLabel = new Label();
        roleValueLabel = new Label();
        birthdayValueLabel = new Label();
        layout = new GridLayout(2, 5);
    }

    @Override
    public void createView() {
        User user = VaadinSession.getCurrent().getAttribute(User.class);
        firstnameValueLabel.setValue(user.getSurname());
        lastnameValueLabel.setValue(user.getName());
        emailValueLabel.setValue(user.getEmail());
        birthdayValueLabel.setValue(user.getDateOfBirth().toString());

        if (user instanceof Relative) {
            roleValueLabel.setValue(MessageHandler.RELATIVE);
        } else if (user instanceof Patient) {
            roleValueLabel.setValue(MessageHandler.PATIENT);
        } else if (user instanceof Doctor) {
            roleValueLabel.setValue(MessageHandler.DOCTOR);
        }

        layout.addComponent(firstnameLabel, 0, 0);
        layout.addComponent(firstnameValueLabel, 1, 0);
        layout.addComponent(lastnameLabel, 0, 1);
        layout.addComponent(lastnameValueLabel, 1, 1);
        layout.addComponent(emailLabel, 0, 2);
        layout.addComponent(emailValueLabel, 1, 2);
        layout.addComponent(birthdayLabel, 0, 3);
        layout.addComponent(birthdayValueLabel, 1, 3);
        layout.addComponent(roleLabel, 0, 4);
        layout.addComponent(roleValueLabel, 1, 4);

        addComponent(layout);
    }
}
