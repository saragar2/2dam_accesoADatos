package org.educa.dao;

import org.educa.entity.SucursalEntity;

import java.sql.SQLException;
import java.util.List;

// Interfaz para las operaciones relacionadas con las sucursales
public interface SucursalDAO {
    // Método para obtener una lista de todas las sucursales
    List<SucursalEntity> findAll() throws SQLException;
}
