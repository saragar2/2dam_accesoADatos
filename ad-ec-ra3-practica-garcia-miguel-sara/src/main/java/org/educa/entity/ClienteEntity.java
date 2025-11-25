package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteEntity {

    private Integer id;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String email;
    private String dni;
    private String telefono;
    private List<AlquilerEntity> alquileres;
    private List<DireccionEntity> direcciones;

}