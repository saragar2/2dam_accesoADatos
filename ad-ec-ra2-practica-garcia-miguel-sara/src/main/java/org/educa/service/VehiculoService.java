package org.educa.service;

// Clase que gestiona la lógica de negocio relacionada con los vehículos
import org.educa.dao.VehiculoDAO;
import org.educa.dao.VehiculoDAOImpl;
import org.educa.entity.CategoriaEntity;
import org.educa.entity.SucursalEntity;
import org.educa.entity.VehiculoEntity;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class VehiculoService {

    // DAO para acceder a los datos de los vehículos
    private final VehiculoDAO vehiculoDAO = new VehiculoDAOImpl();

    // Método para obtener una lista de vehículos por sucursal
    public List<VehiculoEntity> findBySucursal(SucursalEntity sucursal) throws SQLException {
        return vehiculoDAO.findBySucursal(sucursal);
    }

    // Método para calcular el precio por día de un vehículo
    public BigDecimal getPrecioPorDia(VehiculoEntity vehiculo) {
        try {
            // Verifica si el vehículo o su categoría son nulos
            if (vehiculo == null || vehiculo.getCategoria() == null || vehiculo.getCategoria().getIdCategoria() == null) {
                return BigDecimal.ZERO;
            }
            // Obtiene la categoría del vehículo y calcula el precio según su tipo
            CategoriaEntity categoria = new CategoriaService().findById(vehiculo.getCategoria().getIdCategoria());
            if (categoria == null || categoria.getNombre() == null) return BigDecimal.ZERO;
            String nombre = categoria.getNombre().trim().toUpperCase();
            return switch (nombre) {
                case "M" -> BigDecimal.valueOf(10);
                case "E" -> BigDecimal.valueOf(20);
                case "C" -> BigDecimal.valueOf(70);
                case "S" -> BigDecimal.valueOf(120);
                case "P" -> BigDecimal.valueOf(200);
                default -> BigDecimal.ZERO;
            };
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Método para encontrar un vehículo por su ID
    public VehiculoEntity findById(Integer idVehiculo) throws SQLException {
        return vehiculoDAO.findById(idVehiculo);
    }
}
