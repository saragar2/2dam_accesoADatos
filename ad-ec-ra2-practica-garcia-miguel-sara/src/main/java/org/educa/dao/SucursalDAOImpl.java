package org.educa.dao;

import org.educa.entity.SucursalEntity;
import org.educa.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Implementación de la interfaz SucursalDAO
public class SucursalDAOImpl implements SucursalDAO {

    /**
     * Recoge en una lista todas las sucursales
     * @return La lista de sucursales
     */
    // Método para obtener una lista de todas las sucursales
    @Override
    public List<SucursalEntity> findAll() throws SQLException {
        // Consulta SQL para seleccionar los datos de las sucursales
        String sql = "SELECT id_sucursal, calle, ciudad, pais, c_p FROM sucursal";
        List<SucursalEntity> list = new ArrayList<>();
        try (Connection conn = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            // Procesamiento del resultado de la consulta para crear objetos SucursalEntity
            while (rs.next()) {
                SucursalEntity s = new SucursalEntity();
                s.setIdSucursal(rs.getInt("id_sucursal"));
                s.setCalle(rs.getString("calle"));
                s.setCiudad(rs.getString("ciudad"));
                s.setPais(rs.getString("pais"));
                s.setCodigoPostal(rs.getString("c_p"));
                list.add(s);
            }
        }
        return list;
    }
}
