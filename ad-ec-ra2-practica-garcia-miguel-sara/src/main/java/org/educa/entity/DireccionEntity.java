package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Clase que representa una dirección
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DireccionEntity {
    // Atributo para el identificador único de la dirección
    private Integer idDireccion;
    // Atributo para la calle de la dirección
    private String calle;
    // Atributo para la ciudad de la dirección
    private String ciudad;
    // Atributo para el país de la dirección
    private String pais;
    // Atributo para el código postal de la dirección
    private String cp;
    // Atributo para el identificador del cliente asociado a la dirección
    private Integer idCliente;
}
