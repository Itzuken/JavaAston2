package user_service.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import user_service.models.User;
import user_service.utils.HibernateSessionFactoryUtil;

public class UserDAO {
    public void save(User user){ //persist
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.persist(user);
        tx1.commit();
        session.close();
    }
    public User read(int id){ //find()
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().find(User.class, id);
    }
    public void update(User user){ //merge()
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.merge(user);
        tx1.commit();
        session.close();
    }
    public void delete(User user){ //remove()
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.remove(user);
        tx1.commit();
        session.close();
    }
}
