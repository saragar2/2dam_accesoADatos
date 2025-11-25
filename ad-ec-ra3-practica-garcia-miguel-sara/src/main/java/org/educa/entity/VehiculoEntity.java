package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehiculoEntity {
    private Integer id;
    private String matricula;
    private String bastidor;
    private String marca;
    private String modelo;
    private String color;
    private Integer anio;
    private CategoriaEntity categoria;
    private SucursalEntity sucursal;
    private CombustibleEntity combustible;
    private List<AlquilerEntity> alquileres;
    private List<EquipamientoEntity> equipamientos;
}