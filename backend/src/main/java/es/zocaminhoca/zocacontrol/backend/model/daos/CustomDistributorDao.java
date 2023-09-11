package es.zocaminhoca.zocacontrol.backend.model.daos;

import es.zocaminhoca.zocacontrol.backend.model.entities.Distributor;

import java.time.LocalDate;
import java.util.List;

public interface CustomDistributorDao {

    List<Distributor> findByName(String queryName);

    List<Distributor> findByLastWeekOffers(LocalDate startWeekDate, LocalDate endWeekDate);
}
