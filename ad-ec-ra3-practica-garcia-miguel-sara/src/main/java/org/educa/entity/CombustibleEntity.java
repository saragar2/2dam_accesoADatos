package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CombustibleEntity {

    private Integer id;
    private String nombre;
    private String descripcion;
    private List<VehiculoEntity> vehiculos;

}