package org.educa.dao;

import org.educa.configuration.HibernateConfiguration;
import org.educa.entity.SucursalEntity;
import org.hibernate.Session;

import java.util.List;

public class SucursalDAO {

    public List<SucursalEntity> findAll() {
        try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {
            return session.createQuery("FROM SucursalEntity", SucursalEntity.class).list();
        }
    }
}