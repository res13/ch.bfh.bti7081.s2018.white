package ch.bfh.bti7081.s2018.white.pms.ui.profile;

import ch.bfh.bti7081.s2018.white.pms.ui.main.PmsSecureView;
import com.vaadin.ui.Panel;

public class ProfileView extends PmsSecureView {

    public static final String NAME = "profile";

    public ProfileView() {
        super();
        setCaption(NAME);
    }
}
