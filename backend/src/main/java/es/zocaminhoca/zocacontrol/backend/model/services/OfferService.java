package es.zocaminhoca.zocacontrol.backend.model.services;

import es.zocaminhoca.zocacontrol.backend.model.daos.DistributorDao;
import es.zocaminhoca.zocacontrol.backend.model.daos.OfferDao;
import es.zocaminhoca.zocacontrol.backend.model.daos.OfferLineDao;
import es.zocaminhoca.zocacontrol.backend.model.entities.Distributor;
import es.zocaminhoca.zocacontrol.backend.model.entities.Offer;
import es.zocaminhoca.zocacontrol.backend.util.WeekOfYearOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OfferService {

    @Autowired
    private OfferDao offerDao;

    @Autowired
    private OfferLineDao offerLineDao;

    @Autowired
    private DistributorDao distributorDao;

    public Distributor addDistributor(Distributor distributor) {

        Offer emptyOffer = new Offer(null, null, null);
        distributor.setOffer(emptyOffer);
        distributorDao.save(distributor);
        return distributor;

    }

    public List<Distributor> findDistributorsByName(String queryName) {
        return distributorDao.findByName(queryName);
    }

    public List<Distributor> getDistributors() {

        List<Distributor> distributors =
                StreamSupport.stream(distributorDao.findAll().spliterator(), false).collect(Collectors.toList());

        return distributors;

    }

    public List<Distributor> saveOffers(List<Distributor> distributors) {

        for (Distributor d : distributors) {
            d.getOffer().setDate(LocalDate.now());
            d.getOffer().setWeekOfYear(WeekOfYearOperations.getWeekOfYear(LocalDate.now()));

            distributorDao.save(d);
        }
        return distributors;

    }

    public List<Distributor> getLastWeekOffersAndDistributors() {

        LocalDate pastMonday = WeekOfYearOperations.getFirstDayOfWeek(LocalDate.now().minusDays(7));
        LocalDate nextSunday = WeekOfYearOperations.getLastDayOfWeek(LocalDate.now());

        List<Distributor> distributors = distributorDao.findByLastWeekOffers(pastMonday,
                nextSunday);

        return distributors;
    }

    public List<Distributor> getSavedOffers() {
        LocalDate monday = WeekOfYearOperations.getFirstDayOfWeek(LocalDate.now());
        LocalDate sunday = WeekOfYearOperations.getLastDayOfWeek(LocalDate.now());

        List<Distributor> distributors = distributorDao.findByLastWeekOffers(monday,
                sunday);

        return distributors;

    }


}

