package org.educa.dao;

import org.educa.entity.SucursalEntity;

import java.util.List;

public interface SucursalDAO {
    public void insertar(SucursalEntity sucursal);

    SucursalEntity findByCP(String cp);

    Integer deleteByCP(String cp);

    List<SucursalEntity> findByCalle(String calle);
}
