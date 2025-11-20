package org.educa.dao;

import org.educa.entity.CategoriaEntity;
import org.educa.entity.SucursalEntity;
import org.educa.entity.VehiculoEntity;
import org.educa.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Implementación de la interfaz VehiculoDAO
public class VehiculoDAOImpl implements VehiculoDAO {

    /**
     * Encuentra los vehículos disponibles según la sucursal
     * @param sucursal La sucursal cuyos vehículos queremos
     * @return La lista de vehículos encontrados
     */
    // Método para obtener una lista de vehículos por sucursal
    @Override
    public List<VehiculoEntity> findBySucursal(SucursalEntity sucursal) throws SQLException {
        // Consulta SQL para seleccionar los datos de los vehículos
        String sql = "SELECT id_vehiculo, matricula, bastidor, marca, modelo, color, anio, id_categoria, id_sucursal, id_comb FROM vehiculo WHERE id_sucursal = ?";
        List<VehiculoEntity> list = new ArrayList<>();
        try (Connection conn = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // Configuración de los parámetros de la consulta
            ps.setInt(1, sucursal.getIdSucursal());
            try (ResultSet rs = ps.executeQuery()) {
                // Procesamiento del resultado de la consulta para crear objetos VehiculoEntity
                while (rs.next()) {
                    VehiculoEntity v = new VehiculoEntity();
                    v.setIdVehiculo(rs.getInt("id_vehiculo"));
                    v.setMatricula(rs.getString("matricula"));
                    v.setBastidor(rs.getString("bastidor"));
                    v.setMarca(rs.getString("marca"));
                    v.setModelo(rs.getString("modelo"));
                    v.setColor(rs.getString("color"));
                    v.setAnio(rs.getInt("anio"));
                    CategoriaEntity c = new CategoriaEntity();
                    c.setIdCategoria(rs.getInt("id_categoria"));
                    v.setCategoria(c);
                    SucursalEntity s = new SucursalEntity();
                    s.setIdSucursal(rs.getInt("id_sucursal"));
                    v.setSucursal(s);
                    v.setIdComb(rs.getInt("id_comb"));
                    list.add(v);
                }
            }
        }
        return list;
    }

    /**
     * Encuentra un vehículo según su id
     * @param idVehiculo El id del vehículo
     * @return El vehículo que se buscaba
     * @throws SQLException
     */
    // Método para encontrar un vehículo por su ID
    @Override
    public VehiculoEntity findById(Integer idVehiculo) throws SQLException {
        // Consulta SQL para seleccionar los datos del vehículo
        String sql = "SELECT id_vehiculo, matricula, bastidor, marca, modelo, color, anio, id_categoria, id_sucursal, id_comb FROM vehiculo WHERE id_vehiculo = ?";
        try (Connection conn = ConnectionPool.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // Configuración de los parámetros de la consulta
            ps.setInt(1, idVehiculo);
            try (ResultSet rs = ps.executeQuery()) {
                // Procesamiento del resultado de la consulta para crear un objeto VehiculoEntity
                if (rs.next()) {
                    VehiculoEntity v = new VehiculoEntity();
                    v.setIdVehiculo(rs.getInt("id_vehiculo"));
                    v.setMatricula(rs.getString("matricula"));
                    v.setBastidor(rs.getString("bastidor"));
                    v.setMarca(rs.getString("marca"));
                    v.setModelo(rs.getString("modelo"));
                    v.setColor(rs.getString("color"));
                    v.setAnio(rs.getInt("anio"));
                    CategoriaEntity c = new CategoriaEntity();
                    c.setIdCategoria(rs.getInt("id_categoria"));
                    v.setCategoria(c);
                    SucursalEntity s = new SucursalEntity();
                    s.setIdSucursal(rs.getInt("id_sucursal"));
                    v.setSucursal(s);
                    v.setIdComb(rs.getInt("id_comb"));
                    return v;
                }
            }
        }
        return null;
    }
}
