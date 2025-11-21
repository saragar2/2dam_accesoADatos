package org.educa.dao;

import org.educa.entity.VehiculoEntity;

import java.sql.SQLException;
import java.util.List;

public interface VehiculoDAO {

    /**
     * Looks for a vehicle with the same plate as the given as 'matricula'
     *
     * @param matricula The plate given
     * @param yearsOld  How old (in years) is the vehicle
     * @return The vehicle found
     */
    VehiculoEntity findByMatricula(String matricula, int yearsOld);

    /**
     * Deletes all the vehicles given as 'vehiculos'
     *
     * @param vehiculos A list of vehicles
     * @throws SQLException SQLException
     */
    void deleteAll(List<VehiculoEntity> vehiculos) throws SQLException;
}
