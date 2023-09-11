package es.zocaminhoca.zocacontrol.backend.model.daos;

import es.zocaminhoca.zocacontrol.backend.model.entities.Clientes;

import java.util.List;

public interface CustomClientesDao {

    List<Clientes> findByName(String queryName);

    List<Clientes> findAllFechabajaIsNull();
}
