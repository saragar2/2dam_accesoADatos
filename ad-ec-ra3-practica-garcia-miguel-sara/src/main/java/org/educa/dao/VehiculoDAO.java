package org.educa.dao;

import org.educa.configuration.HibernateConfiguration;
import org.educa.entity.SucursalEntity;
import org.educa.entity.VehiculoEntity;
import org.hibernate.Session;

import java.util.List;

public class VehiculoDAO {

    public List<VehiculoEntity> findBySucursal(SucursalEntity sucursal) {
        try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {
            return session.createQuery("FROM VehiculoEntity WHERE sucursal.id = :sucursalId", VehiculoEntity.class)
                    .setParameter("sucursalId", sucursal.getId())
                    .list();
        }
    }
}