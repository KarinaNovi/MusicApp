package com.novi.app.model.DAO.impl;

import com.novi.app.model.DAO.UserDAO;
import com.novi.app.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOImpl.class);

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User findById(int userId){
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, userId);
    }

    @Override
    public void save(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            UserDAOImpl.LOGGER.info("User successfully added");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                LOGGER.error("Cannot save user"); // more reasons
            }
        }
    }

    @Override
    public void update(int userId, User updateUser) {
    }

    @Override
    public void deleteById(int userId) {
        Session session = sessionFactory.getCurrentSession();
        User user = findById(userId);
        session.delete(user);
    }

    @Override
    public List<User> findAll() {
        Session session = sessionFactory.getCurrentSession();
        String sql = "SELECT e FROM " + User.class.getName() + " e ";
        Query query = session.createQuery(sql);
        return (List<User>) query.getResultList();
    }
}
