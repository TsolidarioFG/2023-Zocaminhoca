package es.zocaminhoca.zocacontrol.backend.model.daos;

import es.zocaminhoca.zocacontrol.backend.model.entities.BillLine;
import org.springframework.data.repository.CrudRepository;

public interface BillLineDao extends CrudRepository<BillLine, Long> {
}
