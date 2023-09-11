package es.zocaminhoca.zocacontrol.backend.model.daos;

import es.zocaminhoca.zocacontrol.backend.model.entities.Offer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OfferDao extends CrudRepository<Offer, Long> {

    List<Offer> findByWeekOfYear(int weekOfYear);
    
}
