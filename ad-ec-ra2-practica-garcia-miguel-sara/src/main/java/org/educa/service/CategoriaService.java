package org.educa.service;

// Clase que gestiona la lógica de negocio relacionada con las categorías
import org.educa.dao.CategoriaDAO;
import org.educa.dao.CategoriaDAOImpl;
import org.educa.entity.CategoriaEntity;

import java.sql.SQLException;

public class CategoriaService {

    // DAO para acceder a los datos de las categorías
    private final CategoriaDAO categoriaDAO = new CategoriaDAOImpl();

    // Método para encontrar una categoría por su ID
    public CategoriaEntity findById(Integer idCategoria) throws SQLException {
        return categoriaDAO.findById(idCategoria);
    }
}
