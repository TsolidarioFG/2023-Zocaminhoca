package es.zocaminhoca.zocacontrol.backend.model.daos;

import es.zocaminhoca.zocacontrol.backend.model.entities.Receipt;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReceiptDao extends CrudRepository<Receipt, Long> {

    public Receipt findTopByClientCodclienteOrderByDateDesc(String codclient);

    public Receipt findTopByClientCodclienteAndStateOrderByDateDesc(String codclient, String state);

    public List<Receipt> findByStateAndDateGreaterThanEqualOrderByPriceAsc(String estado,
                                                                           LocalDate date);

    public List<Receipt> findByState(String state);

}
