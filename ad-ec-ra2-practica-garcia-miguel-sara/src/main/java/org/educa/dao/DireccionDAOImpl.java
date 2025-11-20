package org.educa.dao;

import org.educa.entity.DireccionEntity;

import java.sql.*;

public class DireccionDAOImpl implements DireccionDAO {

    // Implementación de la interfaz DireccionDAO
    /**
     * Añade a la base de datos una nueva dirección
     * @param connection Conexión a la base de datos
     * @param direccion La dirección a añadir
     * @return El id de la dirección, o -1 en caso de error
     */
    @Override
    public int save(Connection connection, DireccionEntity direccion) throws SQLException {
        // Consulta SQL para insertar una nueva dirección
        String sql = "INSERT INTO direccion (calle, ciudad, pais, c_p, id_cliente) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Configuración de los parámetros de la consulta
            ps.setString(1, direccion.getCalle());
            ps.setString(2, direccion.getCiudad());
            ps.setString(3, direccion.getPais());
            ps.setString(4, direccion.getCp());
            if (direccion.getIdCliente() != null)
                ps.setInt(5, direccion.getIdCliente());
            else
                ps.setNull(5, Types.INTEGER);
            ps.executeUpdate();
            // Obtención del ID generado para la nueva dirección
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }
}
