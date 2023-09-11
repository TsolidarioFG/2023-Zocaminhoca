package es.zocaminhoca.zocacontrol.backend.model.daos;

import es.zocaminhoca.zocacontrol.backend.model.entities.Distributor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.time.LocalDate;
import java.util.List;

public class CustomDistributorDaoImpl implements CustomDistributorDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Distributor> findByName(String queryName) {

        String queryString =
                "SELECT d FROM Distributor d WHERE LOWER(d.name) LIKE (:name)";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("name", "%" + queryName + "%");
        List<Distributor> distributors = query.getResultList();

        return distributors;

    }

    @Override
    public List<Distributor> findByLastWeekOffers(LocalDate startWeekDate, LocalDate endWeekDate) {

        String queryString =
                "SELECT d FROM Distributor d JOIN d.offer o WHERE o.date BETWEEN :startDate AND " +
                        ":endDate";

        Query query = entityManager.createQuery(queryString);
        query.setParameter("startDate", startWeekDate);
        query.setParameter("endDate", endWeekDate);
        List<Distributor> distributors = query.getResultList();

        return distributors;
    }
}
