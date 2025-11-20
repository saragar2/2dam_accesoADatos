package org.educa.dao;

import org.educa.entity.AlquilerEntity;
import org.educa.entity.ClienteEntity;
import org.educa.entity.SeguroEntity;
import org.educa.entity.VehiculoEntity;
import org.educa.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Implementación de la interfaz AlquilerDAO
public class AlquilerDAOImpl implements AlquilerDAO {

    /**
     * Recoge y asigna los atributos de los alquileres según el id del cliente
     * @param cliente El cliente cuya información queremos
     * @return La lista de alquileres anteriores del cliente
     */
    @Override
    // Método para obtener los alquileres de un cliente específico
    public List<AlquilerEntity> findByCliente(ClienteEntity cliente) throws SQLException {
        // Consulta SQL para seleccionar los datos de los alquileres
        String sql = "SELECT a.id_alquiler, a.fecha_ini, a.fecha_fin, a.precio, v.id_vehiculo," +
                " v.marca, v.modelo, v.color, s.id_seguro, s.nombre as seguro_nombre " +
                "FROM alquiler a " +
                "LEFT JOIN vehiculo v ON a.id_vehiculo = v.id_vehiculo " +
                "LEFT JOIN seguro s ON a.id_seguro = s.id_seguro " +
                "WHERE a.id_cliente = ?";
        List<AlquilerEntity> result = new ArrayList<>();
        try (Connection conn = ConnectionPool.getDataSource().getConnection();
             // Configuración de los parámetros de la consulta
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cliente.getIdCliente());
            try (ResultSet rs = ps.executeQuery()) {
                // Procesamiento del resultado de la consulta para crear objetos AlquilerEntity
                while (rs.next()) {
                    AlquilerEntity a = new AlquilerEntity();
                    a.setIdAlquiler(rs.getInt("id_alquiler"));
                    Date d1 = rs.getDate("fecha_ini");
                    if (d1 != null) a.setFechaIni(d1.toLocalDate());
                    Date d2 = rs.getDate("fecha_fin");
                    if (d2 != null) a.setFechaFin(d2.toLocalDate());
                    a.setPrecio(rs.getBigDecimal("precio"));

                    VehiculoEntity v = new VehiculoEntity();
                    v.setIdVehiculo(rs.getInt("id_vehiculo"));
                    v.setMarca(rs.getString("marca"));
                    v.setModelo(rs.getString("modelo"));
                    v.setColor(rs.getString("color"));
                    a.setVehiculo(v);

                    SeguroEntity s = new SeguroEntity();
                    s.setIdSeguro(rs.getInt("id_seguro"));
                    s.setNombre(rs.getString("seguro_nombre"));
                    a.setSeguro(s);

                    result.add(a);
                }
            }
        }
        return result;
    }

    /**
     * Guarda la información del nuevo alquiler
     * @param alquiler EL alquiler que queremos añadir
     * @return El id del nuevo alquiler, o -1 en caso de error
     */
    @Override
    // Método para guardar un nuevo alquiler en la base de datos
    public int save(AlquilerEntity alquiler) throws SQLException {
        // Consulta SQL para insertar un nuevo alquiler
        String sql = "INSERT INTO alquiler (fecha_ini, fecha_fin, id_cliente, id_vehiculo, id_seguro, precio)" +
                " VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionPool.getDataSource().getConnection();
             // Configuración de los parámetros de la consulta
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, Date.valueOf(alquiler.getFechaIni()));
            ps.setDate(2, Date.valueOf(alquiler.getFechaFin()));
            ps.setInt(3, alquiler.getCliente().getIdCliente());
            ps.setInt(4, alquiler.getVehiculo().getIdVehiculo());
            ps.setInt(5, alquiler.getSeguro().getIdSeguro());
            ps.setBigDecimal(6, alquiler.getPrecio());
            ps.executeUpdate();
            // Obtención del ID generado para el nuevo alquiler
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }
}
