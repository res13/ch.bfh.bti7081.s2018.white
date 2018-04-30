package ch.bfh.bti7081.s2018.white.pms.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtility implements AutoCloseable {

    private static final EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("ch.bfh.bti7081.s2018.white.pms");
    }

    public JpaUtility() {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
    }

    public EntityManager getEntityManager(){
        return entityManager;
    }

    @Override
    public void close() throws Exception {
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
