package org.educa.dao;

import org.educa.configuration.HibernateConfiguration;
import org.educa.entity.SucursalEntity;
import org.educa.entity.VehiculoEntity;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class SucursalDAOImpl implements SucursalDAO {
    /**
     * inserta una sucursal a la base de datos
     *
     * @param sucursal la sucursal a añadir
     */
    @Override
    public void insertar(SucursalEntity sucursal) {
        try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {
            session.beginTransaction();
            try {
                session.persist(sucursal);
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw e;
            }
            session.getTransaction().commit();
        }
    }

    /**
     * Busca según el código postal dado una sucursal
     *
     * @param cp El código postal perteneciente a la sucursal
     * @return La sucursal encontrada
     */
    @Override
    public SucursalEntity findByCP(String cp) {
        try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {
            session.beginTransaction();
            String hql = "FROM SucursalEntity s WHERE s.cp = :cp";
            Query<SucursalEntity> query = session.createQuery(hql, SucursalEntity.class)
                    .setParameter("cp", cp)
                    .setReadOnly(true);
            for (VehiculoEntity v : query.uniqueResult().getVehiculos()) {
                Hibernate.initialize(query.uniqueResult().getVehiculos());
            }
            return query.uniqueResult();
        }
    }

    /**
     * Borra la sucursal cuyo codigo postal es el dado
     *
     * @param cp el codigo postal que pertenece a la sucursal a borrar
     * @return ID de la susursal borrada
     */
    @Override
    public Integer deleteByCP(String cp) {
        try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {
            session.beginTransaction();
            String hql = "FROM SucursalEntity s WHERE s.cp = :cp";
            Query<SucursalEntity> query = session.createQuery(hql, SucursalEntity.class)
                    .setParameter("cp", cp)
                    .setReadOnly(true);
            try {
                SucursalEntity s = query.uniqueResult();
                if (s != null) {
                    session.remove(s);
                    session.getTransaction().commit();
                    return s.getId();
                }
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw e;
            }
            return null;
        }
    }

    /**
     * Busca una sucursal cuya calle contenga el texto en Calle
     *
     * @param calle pedazo de texto que debe contener la calle de la sucursal
     * @return lista de sucursales que contienen el texto dado
     */
    @Override
    public List<SucursalEntity> findByCalle(String calle) {
        try (Session session = HibernateConfiguration.getSessionFactory().openSession()) {
            session.beginTransaction();
            String hql = "FROM SucursalEntity s WHERE LOWER(s.calle) LIKE LOWER(:calle)";
            Query<SucursalEntity> query = session.createQuery(hql, SucursalEntity.class)
                    .setParameter("calle", calle)
                    .setReadOnly(true);
            return query.list();
        }
    }
}
