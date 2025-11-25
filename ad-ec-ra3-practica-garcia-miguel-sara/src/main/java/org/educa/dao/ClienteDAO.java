package org.educa.dao;

import org.educa.configuration.HibernateConfiguration;
import org.educa.entity.ClienteEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;

public class ClienteDAO {

    public Optional<ClienteEntity> findByDNI(String dni) {
        try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {
            return session.createQuery("FROM ClienteEntity WHERE dni = :dni", ClienteEntity.class)
                    .setParameter("dni", dni)
                    .uniqueResultOptional();
        }
    }

    public void save(ClienteEntity cliente) {
        Transaction transaction = null;
        try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(cliente);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}