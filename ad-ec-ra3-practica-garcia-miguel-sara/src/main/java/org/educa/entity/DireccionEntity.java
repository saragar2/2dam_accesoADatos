package org.educa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DireccionEntity {

    private Integer id;
    private String calle;
    private String ciudad;
    private String pais;
    private String cp;
    private ClienteEntity cliente;

}