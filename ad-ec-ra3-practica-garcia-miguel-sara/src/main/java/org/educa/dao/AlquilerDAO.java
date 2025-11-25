package org.educa.dao;

import org.educa.configuration.HibernateConfiguration;
import org.educa.entity.AlquilerEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AlquilerDAO {

    public void save(AlquilerEntity alquiler) {
        Transaction transaction = null;
        try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(alquiler);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}