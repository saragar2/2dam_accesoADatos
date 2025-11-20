package org.educa.dao;

import org.educa.entity.AlquilerEntity;
import org.educa.entity.ClienteEntity;

import java.sql.SQLException;
import java.util.List;

// Interfaz para las operaciones relacionadas con los alquileres
public interface AlquilerDAO {
    // Método para obtener una lista de alquileres de un cliente específico
    List<AlquilerEntity> findByCliente(ClienteEntity cliente) throws SQLException;

    // Método para guardar un nuevo alquiler en la base de datos
    int save(AlquilerEntity alquiler) throws SQLException;
}
