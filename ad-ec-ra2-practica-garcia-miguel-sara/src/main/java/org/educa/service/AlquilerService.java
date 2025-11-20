package org.educa.service;

import org.educa.dao.AlquilerDAO;
import org.educa.dao.AlquilerDAOImpl;
import org.educa.entity.AlquilerEntity;
import org.educa.entity.ClienteEntity;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.util.List;

// Clase que gestiona la lógica de negocio relacionada con los alquileres
public class AlquilerService {
    // Constante para el descuento aplicado a clientes frecuentes
    private static final BigDecimal DESCUENTO = BigDecimal.valueOf(0.05);

    // DAO para acceder a los datos de los alquileres
    private final AlquilerDAO alquilerDAO = new AlquilerDAOImpl();

    // Método para obtener los alquileres de un cliente específico
    public List<AlquilerEntity> findByCliente(ClienteEntity cliente) throws SQLException {
        return alquilerDAO.findByCliente(cliente);
    }

    // Método para guardar un nuevo alquiler en la base de datos
    public void save(AlquilerEntity alquiler) throws SQLException {
        int id = alquilerDAO.save(alquiler);
        alquiler.setIdAlquiler(id);
    }

    // Método para calcular el precio total de un alquiler
    public void calculatePrecio(AlquilerEntity alquiler, int numAlquileres) {
        // Calcula la cantidad de días entre las fechas de inicio y fin
        long dias = ChronoUnit.DAYS.between(alquiler.getFechaIni(), alquiler.getFechaFin());
        if (dias <= 0) dias = 1;

        // Obtiene el precio por día del vehículo alquilado
        BigDecimal precioVehiculo = new VehiculoService().getPrecioPorDia(alquiler.getVehiculo());
        // Obtiene el precio por día del seguro seleccionado
        BigDecimal precioSeguro = new SeguroService().getPrecioPorDia(alquiler.getSeguro());

        // Calcula el precio total multiplicando el precio diario por los días
        BigDecimal totalPorDia = precioVehiculo.add(precioSeguro);
        BigDecimal total = totalPorDia.multiply(BigDecimal.valueOf(dias));

        // Aplica un descuento si el cliente tiene más de 10 alquileres previos
        if (numAlquileres > 10) {
            total = total.multiply(BigDecimal.ONE.subtract(DESCUENTO));
        }

        alquiler.setPrecio(total);
    }
}
