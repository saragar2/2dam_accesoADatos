package org.educa.service;

// Clase que gestiona la lógica de negocio relacionada con los seguros
import org.educa.dao.SeguroDAO;
import org.educa.dao.SeguroDAOImpl;
import org.educa.entity.SeguroEntity;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class SeguroService {

    // DAO para acceder a los datos de los seguros
    private final SeguroDAO seguroDAO = new SeguroDAOImpl();

    // Método para obtener una lista de todos los seguros
    public List<SeguroEntity> findAll() throws SQLException {
        return seguroDAO.findAll();
    }

    // Método para encontrar un seguro por su ID
    public SeguroEntity findById(Integer idSeguro) throws SQLException {
        return seguroDAO.findById(idSeguro);
    }

    // Método para calcular el precio por día de un seguro
    public BigDecimal getPrecioPorDia(SeguroEntity seguro) {
        // Verifica si el seguro o su nombre son nulos
        if (seguro == null || seguro.getNombre() == null) return BigDecimal.ZERO;
        String nombre = seguro.getNombre().toLowerCase();
        // Calcula el precio según el tipo de seguro
        return switch (nombre) {
            case "a terceros" -> BigDecimal.valueOf(5);
            case "franquicia 300" -> BigDecimal.valueOf(20);
            case "franquicia 150" -> BigDecimal.valueOf(35);
            case "a todo riesgo" -> BigDecimal.valueOf(50);
            default -> BigDecimal.ZERO;
        };
    }
}
