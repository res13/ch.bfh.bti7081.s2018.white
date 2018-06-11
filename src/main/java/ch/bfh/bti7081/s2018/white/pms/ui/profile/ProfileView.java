package ch.bfh.bti7081.s2018.white.pms.ui.profile;

import ch.bfh.bti7081.s2018.white.pms.common.i18n.MessageHandler;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Doctor;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Patient;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Relative;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import ch.bfh.bti7081.s2018.white.pms.ui.main.PmsSecureView;

import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

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
    private Image profileImage;
    private GridLayout grid;
    private VerticalLayout layout;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void initialize() {
        lastnameLabel = new Label(MessageHandler.SURNAME);
        firstnameLabel = new Label(MessageHandler.NAME);
        emailLabel = new Label(MessageHandler.EMAIL);
        roleLabel = new Label(MessageHandler.STATUS);
        birthdayLabel = new Label(MessageHandler.BIRTHDAY);
        firstnameValueLabel = new Label();
        lastnameValueLabel = new Label();
        emailValueLabel = new Label();
        roleValueLabel = new Label();
        birthdayValueLabel = new Label();
        grid = new GridLayout(2, 5);
        layout = new VerticalLayout();
        profileImage = new Image("", new ThemeResource("images/profile.png"));
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

        grid.addComponent(firstnameLabel, 0, 0);
        grid.addComponent(firstnameValueLabel, 1, 0);
        grid.addComponent(lastnameLabel, 0, 1);
        grid.addComponent(lastnameValueLabel, 1, 1);
        grid.addComponent(emailLabel, 0, 2);
        grid.addComponent(emailValueLabel, 1, 2);
        grid.addComponent(birthdayLabel, 0, 3);
        grid.addComponent(birthdayValueLabel, 1, 3);
        grid.addComponent(roleLabel, 0, 4);
        grid.addComponent(roleValueLabel, 1, 4);
        layout.addComponent(profileImage);
        layout.addComponent(grid);
        
        addComponent(layout);
    }
}
