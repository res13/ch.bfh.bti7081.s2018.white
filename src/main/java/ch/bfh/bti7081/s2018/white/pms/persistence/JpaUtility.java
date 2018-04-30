package ch.bfh.bti7081.s2018.white.pms.persistence;

import ch.bfh.bti7081.s2018.white.pms.ui.MainUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtility implements AutoCloseable {

    public static final Logger log = LogManager.getLogger(MainUI.class.getName());

    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    public JpaUtility() {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("ch.bfh.bti7081.s2018.white.pms");
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
        }
        catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    public EntityManager getEntityManager(){
        return entityManager;
    }

    @Override
    public void close() throws Exception {
        if (entityManager != null) {
            entityManager.getTransaction().commit();
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
