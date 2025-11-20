package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Clase que representa un seguro
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeguroEntity {
    // Atributo para el identificador único del seguro
    private Integer idSeguro;
    // Atributo para el nombre del seguro
    private String nombre;
    // Atributo para la descripción del seguro
    private String descripcion;
}

