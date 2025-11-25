package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AlquilerEntity {

    private Integer id;
    private LocalDate fechaIni;
    private LocalDate fechaFin;
    private BigDecimal precio;
    private ClienteEntity cliente;
    private VehiculoEntity vehiculo;
    private SeguroEntity seguro;

}