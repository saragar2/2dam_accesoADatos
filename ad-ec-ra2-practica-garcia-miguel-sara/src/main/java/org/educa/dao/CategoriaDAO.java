package org.educa.dao;

import org.educa.entity.CategoriaEntity;

import java.sql.SQLException;

// Interfaz para las operaciones relacionadas con las categorías
public interface CategoriaDAO {
    // Método para encontrar una categoría por su ID
    CategoriaEntity findById(Integer idCategoria) throws SQLException;
}
