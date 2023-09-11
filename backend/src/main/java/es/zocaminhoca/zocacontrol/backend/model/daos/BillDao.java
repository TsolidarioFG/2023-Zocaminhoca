package es.zocaminhoca.zocacontrol.backend.model.daos;

import es.zocaminhoca.zocacontrol.backend.model.entities.Bill;
import org.springframework.data.repository.CrudRepository;

public interface BillDao extends CrudRepository<Bill, Long> {
}
