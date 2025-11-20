package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

// Clase que representa un cliente
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEntity {
    // Atributo para el identificador único del cliente
    private Integer idCliente;
    // Atributo para el nombre del cliente
    private String nombre;
    // Atributo para el primer apellido del cliente
    private String primerApellido;
    // Atributo para el segundo apellido del cliente
    private String segundoApellido;
    // Atributo para el correo electrónico del cliente
    private String email;
    // Atributo para el DNI del cliente
    private String dni;
    // Atributo para el teléfono del cliente
    private String telefono;
    // Lista de direcciones asociadas al cliente
    private List<DireccionEntity> direcciones = new ArrayList<>();
}

