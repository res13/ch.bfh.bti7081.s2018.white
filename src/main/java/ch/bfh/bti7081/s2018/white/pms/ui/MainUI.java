package ch.bfh.bti7081.s2018.white.pms.ui;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.servlet.annotation.WebServlet;
import java.util.Date;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MainUI extends UI {

    public static final Logger log = LogManager.getLogger(MainUI.class.getName());


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        log.info("Starting GUI {}" , new Date());
        final VerticalLayout layout = new VerticalLayout();
        
        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener(e -> {
            layout.addComponent(new Label("Thanks " + name.getValue() 
                    + ", it works!"));
        });
        
        layout.addComponents(name, button);
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    public static void main(String[] args) {
        log.info("hoi");

        // Creating Configuration Instance & Passing Hibernate Configuration File
        Configuration configObj = new Configuration();
        SessionFactory sessionFactoryObj = configObj.configure().buildSessionFactory();

        Session currentSession = sessionFactoryObj.openSession();

        DiaryEntry diaryEntry = new DiaryEntry();
        diaryEntry.setTitle("Abc");
        currentSession.save(diaryEntry);

        DiaryEntry diaryEntry1 = currentSession.find(DiaryEntry.class, 1L);

        System.out.println(diaryEntry1.getTitle());
        System.exit(0);
    }
}
