package es.zocaminhoca.zocacontrol.backend.model.daos;

import es.zocaminhoca.zocacontrol.backend.model.entities.Clientes;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientesDao extends CrudRepository<Clientes, String>, CustomClientesDao {

}
