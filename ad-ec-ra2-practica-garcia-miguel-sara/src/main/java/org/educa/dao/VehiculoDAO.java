package org.educa.dao;

import org.educa.entity.SucursalEntity;
import org.educa.entity.VehiculoEntity;

import java.sql.SQLException;
import java.util.List;

// Interfaz para las operaciones relacionadas con los vehículos
public interface VehiculoDAO {
    // Método para obtener una lista de vehículos por sucursal
    List<VehiculoEntity> findBySucursal(SucursalEntity sucursal) throws SQLException;

    // Método para encontrar un vehículo por su ID
    VehiculoEntity findById(Integer idVehiculo) throws SQLException;
}
