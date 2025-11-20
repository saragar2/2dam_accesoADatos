package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

// Clase que representa un alquiler
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlquilerEntity {
    // Atributo para el identificador único del alquiler
    private Integer idAlquiler;
    // Atributo para la fecha de inicio del alquiler
    private LocalDate fechaIni;
    // Atributo para la fecha de fin del alquiler
    private LocalDate fechaFin;
    // Atributo para el cliente asociado al alquiler
    private ClienteEntity cliente = new ClienteEntity();
    // Atributo para el vehículo asociado al alquiler
    private VehiculoEntity vehiculo = new VehiculoEntity();
    // Atributo para el seguro asociado al alquiler
    private SeguroEntity seguro = new SeguroEntity();
    // Atributo para el precio total del alquiler
    private BigDecimal precio;
}

