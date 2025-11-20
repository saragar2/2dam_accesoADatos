package org.educa.dao;

import org.educa.entity.SeguroEntity;

import java.sql.SQLException;
import java.util.List;

// Interfaz para las operaciones relacionadas con los seguros
public interface SeguroDAO {
    // Método para obtener una lista de todos los seguros
    List<SeguroEntity> findAll() throws SQLException;

    // Método para encontrar un seguro por su ID
    SeguroEntity findById(Integer idSeguro) throws SQLException;
}
