package ch.bfh.bti7081.s2018.white.pms.ui;

import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry;
import ch.bfh.bti7081.s2018.white.pms.common.model.app.diary.DiaryEntry_;
import ch.bfh.bti7081.s2018.white.pms.persistence.JpaUtility;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.annotation.WebServlet;
import java.util.Date;
import java.util.List;

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
        log.info("Starting GUI {}", new Date());
        final VerticalLayout layout = new VerticalLayout();

        final TextField name = new TextField();
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener(e -> {
            layout.addComponent(new Label("Thanks " + name.getValue()
                    + ", it works!"));

            testDb();
        });

        layout.addComponents(name, button);
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/test", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    public static void main(String[] args) {
        testDb();
    }

    private static void testDb() {
        String titleText = "ABC";
        DiaryEntry diaryEntry = new DiaryEntry();
        diaryEntry.setTitle(titleText);
        try {
            new JpaUtility().execute(
                    (em) -> {
                        em.persist(diaryEntry);
                        return null;
                    });
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }

        try {
            DiaryEntry diaryEntry1 = new JpaUtility().execute(
                    (em) -> {
                        return em.find(DiaryEntry.class, 1L);
                    });
            System.out.println(diaryEntry1.getTitle());
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }

        try {
            List<DiaryEntry> resultList = new JpaUtility().execute(
                    (em) -> {
                        CriteriaBuilder cb = em.getCriteriaBuilder();
                        CriteriaQuery<DiaryEntry> q = cb.createQuery(DiaryEntry.class);
                        Root<DiaryEntry> root = q.from(DiaryEntry.class);
                        q.where(cb.like(root.get(DiaryEntry_.title), titleText));
                        return em.createQuery(q).getResultList();
                    });
            System.out.println(resultList.size());
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }
}
