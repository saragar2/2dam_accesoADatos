package org.educa.dao;

import org.educa.entity.VehiculoEntity;
import org.educa.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class VehiculoDAOImpl implements VehiculoDAO {

    @Override
    public VehiculoEntity findByMatricula(String matricula, int yearsOld) {
        String sql = "SELECT * " +
                "FROM vehiculo " +
                "WHERE (matricula = ? AND anio < ? - ?)";
        try (Connection conn = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, matricula);
            ps.setInt(2, LocalDate.now().getYear());
            ps.setInt(3, yearsOld);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    VehiculoEntity v = new VehiculoEntity();
                    v.setIdVehiculo(rs.getInt(1));
                    v.setMatricula(rs.getString(2));
                    v.setBastidor(rs.getString(3));
                    v.setMarca(rs.getString(4));
                    v.setModelo(rs.getString(5));
                    v.setColor(rs.getString(6));
                    v.setAnio(rs.getInt(7));
                    v.setIdCategoria(rs.getInt(8));
                    v.setIdSucursal(rs.getInt(9));
                    v.setIdCombustible(rs.getInt(10));
                    return v;
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll(List<VehiculoEntity> vehiculos) throws SQLException {
        String sql = "DELETE FROM vehiculo " +
                "WHERE id_vehiculo = ?";

        try (Connection conn = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            for (VehiculoEntity v : vehiculos) {
                ps.setInt(1, v.getIdVehiculo());
                ps.addBatch();
            }

            ps.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            throw e;
        }
    }
}
