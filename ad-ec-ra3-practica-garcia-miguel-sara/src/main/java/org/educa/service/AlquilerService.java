package org.educa.service;

import org.educa.dao.AlquilerDAO;
import org.educa.entity.AlquilerEntity;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

public class AlquilerService {

    private final AlquilerDAO alquilerDAO = new AlquilerDAO();

    public void save(AlquilerEntity alquiler) {
        alquilerDAO.save(alquiler);
    }

    public void calculatePrecio(AlquilerEntity alquiler, int numAlquileres) {
        long dias = ChronoUnit.DAYS.between(alquiler.getFechaIni(), alquiler.getFechaFin());
        BigDecimal precioVehiculo = alquiler.getVehiculo().getCategoria().getNombre().equals("M")
                ? BigDecimal.valueOf(10)
                : BigDecimal.valueOf(20); // Ejemplo simplificado
        BigDecimal precioSeguro = BigDecimal.valueOf(5); // Ejemplo simplificado

        BigDecimal precioTotal = precioVehiculo.multiply(BigDecimal.valueOf(dias))
                .add(precioSeguro.multiply(BigDecimal.valueOf(dias)));

        if (numAlquileres > 10) {
            precioTotal = precioTotal.multiply(BigDecimal.valueOf(0.95));
        }

        alquiler.setPrecio(precioTotal);
    }
}
