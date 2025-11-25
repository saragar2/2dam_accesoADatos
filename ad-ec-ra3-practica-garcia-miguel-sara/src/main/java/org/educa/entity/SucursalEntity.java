package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SucursalEntity {
    private Integer id;
    private String calle;
    private String ciudad;
    private String pais;
    private String cp;
    private List<VehiculoEntity> vehiculos;

}