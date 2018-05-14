package ch.bfh.bti7081.s2018.white.pms.ui.common;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

import static com.vaadin.ui.Notification.Type.TRAY_NOTIFICATION;

public class Notifier {

    public static void notify(String title, String text){
        Notification sample = new Notification(title, text, TRAY_NOTIFICATION);
        sample.show(Page.getCurrent());
    }

}
