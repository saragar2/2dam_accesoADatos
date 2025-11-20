package org.educa.dao;

import org.educa.entity.ClienteEntity;

import java.sql.Connection;
import java.sql.SQLException;

// Interfaz para las operaciones relacionadas con los clientes
public interface ClienteDAO {

    // Método para encontrar un cliente por su DNI
    ClienteEntity findByDNI(String dni) throws SQLException;

    // Método para guardar un nuevo cliente en la base de datos
    int save(Connection connection, ClienteEntity cliente) throws SQLException;
}
