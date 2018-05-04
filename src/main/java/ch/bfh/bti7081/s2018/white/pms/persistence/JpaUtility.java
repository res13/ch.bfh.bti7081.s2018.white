package ch.bfh.bti7081.s2018.white.pms.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaUtility {

    public static final Logger log = LogManager.getLogger(JpaUtility.class.getName());

    private static EntityManagerFactory entityManagerFactory;

    static {
        try {
            log.info("Initializing database");
            entityManagerFactory = Persistence.createEntityManagerFactory("ch.bfh.bti7081.s2018.white.pms");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    public interface ABlockOfCode<T> {
        T execute(EntityManager em);
    }

    public <T> T execute(ABlockOfCode<T> aBlockOfCode) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            T returnValue = aBlockOfCode.execute(em);
            tx.commit();
            return returnValue;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            log.error(e);
            throw e;
        } finally {
            em.close();
        }
    }
}
