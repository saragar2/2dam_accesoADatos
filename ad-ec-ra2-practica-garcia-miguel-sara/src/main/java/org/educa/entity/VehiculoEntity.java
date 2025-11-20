package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Clase que representa un vehículo
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoEntity {
    // Atributo para el identificador único del vehículo
    private Integer idVehiculo;
    // Atributo para la matrícula del vehículo
    private String matricula;
    // Atributo para el número de bastidor del vehículo
    private String bastidor;
    // Atributo para la marca del vehículo
    private String marca;
    // Atributo para el modelo del vehículo
    private String modelo;
    // Atributo para el color del vehículo
    private String color;
    // Atributo para el año de fabricación del vehículo
    private int anio;
    // Atributo para la categoría asociada al vehículo
    private CategoriaEntity categoria = new CategoriaEntity();
    // Atributo para la sucursal asociada al vehículo
    private SucursalEntity sucursal = new SucursalEntity();
    // Atributo para el identificador del combustible del vehículo
    private int idComb;
}

