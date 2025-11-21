package org.educa.service;

import org.educa.dao.VehiculoDAO;
import org.educa.dao.VehiculoDAOImpl;
import org.educa.entity.VehiculoEntity;

import java.sql.SQLException;
import java.util.List;

public class VehiculoService {

    private final VehiculoDAO vehiculoDAO = new VehiculoDAOImpl();

    /**
     * Looks for a vehicle with the same plate as the given as Matricula
     *
     * @param matricula The plate given
     * @param yearsOld  How old (in years) is the vehicle
     * @return The vehicle found
     * @throws SQLException SQLException
     */
    public VehiculoEntity findByMatricula(String matricula, int yearsOld) throws SQLException {
        return vehiculoDAO.findByMatricula(matricula, yearsOld);
    }

    /**
     * Deletes all the vehicles given as 'vehiculos'
     *
     * @param vehiculos A list of vehicles
     * @throws SQLException SQLException
     */
    public void deleteAll(List<VehiculoEntity> vehiculos) throws SQLException {
        vehiculoDAO.deleteAll(vehiculos);
    }
}
