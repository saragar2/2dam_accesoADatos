package org.educa.service;

// Clase que gestiona la lógica de negocio relacionada con las sucursales
import org.educa.dao.SucursalDAO;
import org.educa.dao.SucursalDAOImpl;
import org.educa.entity.SucursalEntity;

import java.sql.SQLException;
import java.util.List;

public class SucursalService {

    // DAO para acceder a los datos de las sucursales
    private final SucursalDAO sucursalDAO = new SucursalDAOImpl();

    // Método para obtener una lista de todas las sucursales
    public List<SucursalEntity> findAll() throws SQLException {
        return sucursalDAO.findAll();
    }
}
