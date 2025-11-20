package org.educa.service;

// Clase que gestiona la lógica de negocio relacionada con las direcciones
import org.educa.dao.DireccionDAO;
import org.educa.dao.DireccionDAOImpl;
import org.educa.entity.ClienteEntity;
import org.educa.entity.DireccionEntity;

import java.sql.Connection;
import java.sql.SQLException;

public class DireccionService {

    // DAO para acceder a los datos de las direcciones
    private final DireccionDAO direccionDAO = new DireccionDAOImpl();

    // Método para guardar las direcciones asociadas a un cliente
    public void saveDirecciones(Connection connection, ClienteEntity cliente) throws SQLException {
        for (DireccionEntity d : cliente.getDirecciones()) {
            // Asocia el ID del cliente a cada dirección antes de guardarla
            d.setIdCliente(cliente.getIdCliente());
            // Guarda cada dirección en la base de datos y actualiza su ID
            int id = direccionDAO.save(connection, d);
            d.setIdDireccion(id);
        }
    }
}
