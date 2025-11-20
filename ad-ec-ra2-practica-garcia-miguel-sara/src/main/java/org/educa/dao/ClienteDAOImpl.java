package org.educa.dao;

import org.educa.entity.ClienteEntity;
import org.educa.pool.ConnectionPool;

import java.sql.*;

// Implementación de la interfaz ClienteDAO
public class ClienteDAOImpl implements ClienteDAO {

    /**
     * Encuentra un cliente según su dni
     * @param dni El dni del cliente
     * @return El cliente buscado
     * @throws SQLException
     */
    // Método para encontrar un cliente por su DNI
    @Override
    public ClienteEntity findByDNI(String dni) throws SQLException {
        // Consulta SQL para seleccionar los datos del cliente
        String sql = "SELECT id_cliente, nombre, p_apellido, s_apellido, email, dni, " +
                "telefono FROM cliente WHERE dni = ?";
        try (Connection conn = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // Configuración de los parámetros de la consulta
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                // Procesamiento del resultado de la consulta para crear un objeto ClienteEntity
                if (rs.next()) {
                    ClienteEntity c = new ClienteEntity();
                    c.setIdCliente(rs.getInt("id_cliente"));
                    c.setNombre(rs.getString("nombre"));
                    c.setPrimerApellido(rs.getString("p_apellido"));
                    c.setSegundoApellido(rs.getString("s_apellido"));
                    c.setEmail(rs.getString("email"));
                    c.setDni(rs.getString("dni"));
                    c.setTelefono(rs.getString("telefono"));
                    return c;
                }
            }
        }
        return null;
    }

    /**
     * Guarda el cliente en la base de datos usando la conexión proporcionada
     * @param connection Conexión a la base de datos
     * @param cliente El nuevo cliente
     * @return el id generado, o -1 en caso de error
     */
    // Método para guardar un nuevo cliente en la base de datos
    @Override
    public int save(Connection connection, ClienteEntity cliente) throws SQLException {
        // Consulta SQL para insertar un nuevo cliente
        String sql = "INSERT INTO cliente (nombre, p_apellido, s_apellido, email, dni, telefono)" +
                " VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Configuración de los parámetros de la consulta
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getPrimerApellido());
            ps.setString(3, cliente.getSegundoApellido());
            ps.setString(4, cliente.getEmail());
            ps.setString(5, cliente.getDni());
            ps.setString(6, cliente.getTelefono());
            ps.executeUpdate();
            // Obtención del ID generado para el nuevo cliente
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }
}
