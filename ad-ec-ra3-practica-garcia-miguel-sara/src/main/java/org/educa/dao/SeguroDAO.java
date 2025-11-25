package org.educa.dao;

import org.educa.configuration.HibernateConfiguration;
import org.educa.entity.SeguroEntity;
import org.hibernate.Session;

import java.util.List;

public class SeguroDAO {

    public List<SeguroEntity> findAll() {
        try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {
            return session.createQuery("FROM SeguroEntity", SeguroEntity.class).list();
        }
    }
}