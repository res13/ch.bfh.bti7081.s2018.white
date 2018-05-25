package ch.bfh.bti7081.s2018.white.pms.ui.profile;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

import ch.bfh.bti7081.s2018.white.pms.common.model.user.Doctor;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Patient;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.Relative;
import ch.bfh.bti7081.s2018.white.pms.common.model.user.User;
import ch.bfh.bti7081.s2018.white.pms.ui.main.PmsSecureView;

public class ProfileView extends PmsSecureView {

    public static final String NAME = "profile";
    private Label firstnameLabel = new Label("Vorname");
    private Label lastnameLabel = new Label("Nachname");
    private Label emailLabel = new Label("eMail");
    private Label roleLabel = new Label("Status");
    private Label firstnameValueLabel = new Label();
    private Label lastnameValueLabel = new Label();
    private Label emailValueLabel = new Label();
    private Label roleValueLabel = new Label();
    private GridLayout layout = new GridLayout(2,4);
    

    public ProfileView() {
        super();
        setCaption(NAME);
        
        
        User user = VaadinSession.getCurrent().getAttribute(User.class);
        firstnameValueLabel.setValue(user.getSurname());
        lastnameValueLabel.setValue(user.getName());
        emailValueLabel.setValue(user.getEmail());
        
        if(user instanceof Relative){
	        roleValueLabel.setValue(Relative.class.getSimpleName());
        } else if(user instanceof Patient){
        	roleValueLabel.setValue(Patient.class.getSimpleName());
        } else if(user instanceof Doctor){
        	roleValueLabel.setValue(Doctor.class.getSimpleName());
        }
        
        layout.addComponent(firstnameLabel, 0, 0);
        layout.addComponent(firstnameValueLabel, 1, 0);
        layout.addComponent(lastnameLabel, 0, 1);
        layout.addComponent(lastnameValueLabel, 1, 1);
        layout.addComponent(emailLabel, 0, 2);
        layout.addComponent(emailValueLabel, 1, 2);
        layout.addComponent(roleLabel, 0, 3);
        layout.addComponent(roleValueLabel, 1, 3);
        
        addComponent(layout);
    }
    
    
}
