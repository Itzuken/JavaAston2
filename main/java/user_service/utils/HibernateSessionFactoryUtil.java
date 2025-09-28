package user_service.utils;

import org.hibernate.cfg.Configuration;
import user_service.models.User;
import org.hibernate.SessionFactory;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;
    private HibernateSessionFactoryUtil(){}
    public static SessionFactory getSessionFactory(){
        if(sessionFactory == null){
            try{
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");
                configuration.addAnnotatedClass(User.class);
                sessionFactory = configuration.buildSessionFactory();
            } catch( Exception e){
                System.err.println("Ошибка инициализации SessionFactory" + e.getMessage());
                e.printStackTrace();
                throw new ExceptionInInitializerError(e);
            }
        }
        return sessionFactory;
    }

    public static void shutdown(){
        if(sessionFactory != null){
            sessionFactory.close();
        }
    }
}
