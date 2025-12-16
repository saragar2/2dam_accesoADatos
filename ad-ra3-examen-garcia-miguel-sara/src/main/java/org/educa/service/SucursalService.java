package org.educa.service;

import org.educa.dao.SucursalDAOImpl;
import org.educa.entity.SucursalEntity;

import java.util.List;

public class SucursalService {

    private final org.educa.dao.SucursalDAO sucursalDAO = new SucursalDAOImpl();

    /**
     * inserta una sucursal a la base de datos
     *
     * @param sucursal la sucursal a añadir
     */
    public void insertar(SucursalEntity sucursal) {
        sucursalDAO.insertar(sucursal);
    }

    /**
     * Busca según el código postal dado una sucursal
     *
     * @param cp El código postal perteneciente a la sucursal
     * @return La sucursal encontrada
     */
    public SucursalEntity findByCP(String cp) {
        return sucursalDAO.findByCP(cp);
    }

    /**
     * Borra la sucursal cuyo codigo postal es el dado
     *
     * @param cp el codigo postal que pertenece a la sucursal a borrar
     * @return ID de la susursal borrada
     */
    public Integer deleteByCP(String cp) {
        return sucursalDAO.deleteByCP(cp);
    }

    /**
     * Busca una sucursal cuya calle contenga el texto en Calle
     *
     * @param texto pedazo de texto que debe contener la calle de la sucursal
     * @return lista de sucursales que contienen el texto dado
     */
    public List<SucursalEntity> findByCalle(String texto) {
        return sucursalDAO.findByCalle("%" + texto + "%");
    }
}
