package ch.bfh.bti7081.s2018.white.pms.services.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    public static final Logger log = LogManager.getLogger(HibernateUtil.class.getName());

    private static SessionFactory sessionFactory = buildSessionFactory();
    private static Session session;

    private static SessionFactory buildSessionFactory() {
        try {
            if (sessionFactory == null) {
                log.info("Create Session Factory");
                Configuration configObj = new Configuration();
                sessionFactory = configObj.configure().buildSessionFactory();
            }
            return sessionFactory;
        } catch (Throwable ex) {
            log.error("Initial SessionFactory creation failed. {}", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

    public static Session getSession(){
        if(session == null){
            session = getSessionFactory().openSession();
        }
        return session;
    }
}