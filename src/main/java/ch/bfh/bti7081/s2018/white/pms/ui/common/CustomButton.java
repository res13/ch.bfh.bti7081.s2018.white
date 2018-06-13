package ch.bfh.bti7081.s2018.white.pms.ui.common;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;

public class CustomButton extends Button {

    public CustomButton(ButtonType buttonType) {
        this.setStyleName("borderless");
        this.setIcon(new ThemeResource(buttonType.getImage()));
        this.setDescription(buttonType.getMessage());
    }

}
