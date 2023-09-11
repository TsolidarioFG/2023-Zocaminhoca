package es.zocaminhoca.zocacontrol.backend.model.daos;

import es.zocaminhoca.zocacontrol.backend.model.entities.Distributor;
import org.springframework.data.repository.CrudRepository;

public interface DistributorDao extends CrudRepository<Distributor, Long>, CustomDistributorDao {
}
