package org.educa.service;

import org.educa.dao.ClienteDAO;
import org.educa.dao.ClienteDAOImpl;
import org.educa.entity.ClienteEntity;
import org.educa.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

// Clase que gestiona la lógica de negocio relacionada con los clientes
public class ClienteService {

    // DAO para acceder a los datos de los clientes
    private final ClienteDAO clienteDAO = new ClienteDAOImpl();

    // Método para encontrar un cliente por su DNI
    public ClienteEntity findByDNI(String dni) throws SQLException {
        return clienteDAO.findByDNI(dni);
    }

    // Método para guardar un cliente en la base de datos
    public void saveCliente(ClienteEntity cliente) throws SQLException {
        // Establece la conexión con la base de datos
        try (Connection connection = ConnectionPool.getDataSource().getConnection()) {
            // Desactiva el modo de autocommit para manejar transacciones manualmente
            boolean previousAuto = connection.getAutoCommit();
            try {
                connection.setAutoCommit(false);
                // Guarda el cliente en la base de datos
                int id = clienteDAO.save(connection, cliente);
                cliente.setIdCliente(id);
                // Guarda las direcciones asociadas al cliente en la misma transacción
                new DireccionService().saveDirecciones(connection, cliente);
                connection.commit();
            } catch (SQLException e) {
                // Realiza un rollback en caso de error
                connection.rollback();
                throw e;
            } finally {
                // Restaura el estado original del autocommit
                connection.setAutoCommit(previousAuto);
            }
        }
    }
}
