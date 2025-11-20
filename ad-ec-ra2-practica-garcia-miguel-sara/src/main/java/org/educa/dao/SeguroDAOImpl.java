package org.educa.dao;

import org.educa.entity.SeguroEntity;
import org.educa.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Implementación de la interfaz SeguroDAO
public class SeguroDAOImpl implements SeguroDAO {

    /**
     * Recoge los tipos de seguro existentes en una lista
     * @return La lista que recoge los tipos de seguro
     */
    // Método para obtener una lista de todos los seguros
    @Override
    public List<SeguroEntity> findAll() throws SQLException {
        // Consulta SQL para seleccionar los datos de los seguros
        String sql = "SELECT id_seguro, nombre, descripcion FROM seguro";
        List<SeguroEntity> list = new ArrayList<>();
        try (Connection conn = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            // Procesamiento del resultado de la consulta para crear objetos SeguroEntity
            while (rs.next()) {
                SeguroEntity s = new SeguroEntity();
                s.setIdSeguro(rs.getInt("id_seguro"));
                s.setNombre(rs.getString("nombre"));
                s.setDescripcion(rs.getString("descripcion"));
                list.add(s);
            }
        }
        return list;
    }

    /**
     * Encuentra un seguro concreto según su id
     * @param idSeguro El id del seguro que buscamos
     * @return El seguro que buscamos
     */
    // Método para encontrar un seguro por su ID
    @Override
    public SeguroEntity findById(Integer idSeguro) throws SQLException {
        // Consulta SQL para seleccionar los datos del seguro
        String sql = "SELECT id_seguro, nombre, descripcion FROM seguro WHERE id_seguro = ?";
        try (Connection conn = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // Configuración de los parámetros de la consulta
            ps.setInt(1, idSeguro);
            try (ResultSet rs = ps.executeQuery()) {
                // Procesamiento del resultado de la consulta para crear un objeto SeguroEntity
                if (rs.next()) {
                    SeguroEntity s = new SeguroEntity();
                    s.setIdSeguro(rs.getInt("id_seguro"));
                    s.setNombre(rs.getString("nombre"));
                    s.setDescripcion(rs.getString("descripcion"));
                    return s;
                }
            }
        }
        return null;
    }
}
