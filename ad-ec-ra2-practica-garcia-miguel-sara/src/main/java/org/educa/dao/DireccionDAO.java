package org.educa.dao;

import org.educa.entity.DireccionEntity;

import java.sql.Connection;
import java.sql.SQLException;

// Interfaz para las operaciones relacionadas con las direcciones
public interface DireccionDAO {
    // Método para guardar una nueva dirección en la base de datos
    int save(Connection connection, DireccionEntity direccion) throws SQLException;
}
