package org.educa.service;

import org.educa.dao.ClienteDAO;
import org.educa.entity.ClienteEntity;

public class ClienteService {

    private final ClienteDAO clienteDAO = new ClienteDAO();

    public ClienteEntity findByDNI(String dni) {
        return clienteDAO.findByDNI(dni).orElse(null);
    }

    public void saveCliente(ClienteEntity cliente) {
        clienteDAO.save(cliente);
    }
}
