package es.zocaminhoca.zocacontrol.backend.model.daos;

import es.zocaminhoca.zocacontrol.backend.model.entities.OfferLine;
import org.springframework.data.repository.CrudRepository;

public interface OfferLineDao extends CrudRepository<OfferLine, Long> {
}
