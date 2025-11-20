package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Clase que representa una sucursal
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SucursalEntity {
    // Atributo para el identificador único de la sucursal
    private int idSucursal;
    // Atributo para la calle de la sucursal
    private String calle;
    // Atributo para la ciudad de la sucursal
    private String ciudad;
    // Atributo para el país de la sucursal
    private String pais;
    // Atributo para el código postal de la sucursal
    private String codigoPostal;
}
