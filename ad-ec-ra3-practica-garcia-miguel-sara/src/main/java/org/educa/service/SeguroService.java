package org.educa.service;

import org.educa.dao.SeguroDAO;
import org.educa.entity.SeguroEntity;

import java.math.BigDecimal;
import java.util.List;

public class SeguroService {

    private final SeguroDAO seguroDAO = new SeguroDAO();

    public List<SeguroEntity> findAll() {
        return seguroDAO.findAll();
    }

    public BigDecimal getPrecioPorDia(SeguroEntity seguro) {
        switch (seguro.getNombre()) {
            case "A terceros":
                return BigDecimal.valueOf(5);
            case "Franquicia 300":
                return BigDecimal.valueOf(20);
            case "Franquicia 150":
                return BigDecimal.valueOf(35);
            case "A todo riesgo":
                return BigDecimal.valueOf(50);
            default:
                throw new IllegalArgumentException("Seguro desconocido: " + seguro.getNombre());
        }
    }
}
