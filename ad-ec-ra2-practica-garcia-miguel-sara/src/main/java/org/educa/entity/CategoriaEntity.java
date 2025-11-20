package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Clase que representa una categoría de vehículo
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaEntity {
    // Atributo para el identificador único de la categoría
    private Integer idCategoria;
    // Atributo para el nombre de la categoría
    private String nombre;
    // Atributo para la descripción de la categoría
    private String descripcion;
}

