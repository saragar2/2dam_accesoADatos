package org.educa.service;

import org.educa.dao.VehiculoDAO;
import org.educa.entity.SucursalEntity;
import org.educa.entity.VehiculoEntity;

import java.math.BigDecimal;
import java.util.List;

public class VehiculoService {

    private final VehiculoDAO vehiculoDAO = new VehiculoDAO();

    public List<VehiculoEntity> findBySucursal(SucursalEntity sucursal) {
        return vehiculoDAO.findBySucursal(sucursal);
    }

    /**
     * Obtiene el precio por día del vehículo dependiendo de la categoria del mismo
     *
     * @param vehiculo el Vehículo
     * @return el precio por día
     */
    public BigDecimal getPrecioPorDia(VehiculoEntity vehiculo) {
        switch (vehiculo.getCategoria().getNombre()) {
            case "M":
                return BigDecimal.valueOf(10);
            case "E":
                return BigDecimal.valueOf(20);
            case "C":
                return BigDecimal.valueOf(70);
            case "S":
                return BigDecimal.valueOf(120);
            case "P":
                return BigDecimal.valueOf(200);
            default:
                throw new IllegalArgumentException("Categoría desconocida: " + vehiculo.getCategoria().getNombre());
        }
    }
}
