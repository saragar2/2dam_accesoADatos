package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoEntity {
    private Integer idVehiculo;
    private String matricula;
    private String bastidor;
    private String marca;
    private String modelo;
    private String color;
    private int anio;
    private Integer idCategoria;
    private Integer idSucursal;
    private Integer idCombustible;
}

