package org.educa.dao;

import org.educa.entity.CategoriaEntity;
import org.educa.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Implementación de la interfaz CategoriaDAO
public class CategoriaDAOImpl implements CategoriaDAO {

    /**
     * Según el id encuentra una categoría
     * @param idCategoria El id de la categoría
     * @return La categoría solicitada
     */
    // Método para encontrar una categoría por su ID
    @Override
    public CategoriaEntity findById(Integer idCategoria) throws SQLException {
        // Consulta SQL para seleccionar los datos de la categoría
        String sql = "SELECT id_categoria, nombre, descripcion FROM categoria WHERE id_categoria = ?";
        try (Connection conn = ConnectionPool.getDataSource().getConnection();
             // Configuración de los parámetros de la consulta
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            try (ResultSet rs = ps.executeQuery()) {
                // Procesamiento del resultado de la consulta para crear un objeto CategoriaEntity
                if (rs.next()) {
                    CategoriaEntity c = new CategoriaEntity();
                    c.setIdCategoria(rs.getInt("id_categoria"));
                    c.setNombre(rs.getString("nombre"));
                    c.setDescripcion(rs.getString("descripcion"));
                    return c;
                }
            }
        }
        return null;
    }
}
