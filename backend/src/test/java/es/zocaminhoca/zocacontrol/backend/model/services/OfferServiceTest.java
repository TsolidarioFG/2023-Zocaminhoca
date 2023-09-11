package es.zocaminhoca.zocacontrol.backend.model.services;

import es.zocaminhoca.zocacontrol.backend.model.daos.DistributorDao;
import es.zocaminhoca.zocacontrol.backend.model.entities.Distributor;
import es.zocaminhoca.zocacontrol.backend.model.entities.Offer;
import es.zocaminhoca.zocacontrol.backend.model.entities.OfferLine;
import es.zocaminhoca.zocacontrol.backend.util.WeekOfYearOperations;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Rollback
public class OfferServiceTest {

    @Autowired
    private OfferService offerService;

    @Autowired
    private DistributorDao distributorDao;


    private Offer createOffer() {
        List<OfferLine> offerLines = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            OfferLine offerLine = new OfferLine(BigDecimal.valueOf(1.15), BigDecimal.valueOf(1),
                    "testProduct", "testProduct");
            offerLines.add(offerLine);
        }
        Offer offer = new Offer(LocalDate.now(),
                WeekOfYearOperations.getWeekOfYear(LocalDate.now()), offerLines);

        return offer;
    }

    @Test
    public void addDistributorTest() {
        Distributor distributor = new Distributor("testDistributor", "test@email.com", "123456",
                "testAddress");
        distributor = offerService.addDistributor(distributor);

        Distributor foundDistributor = distributorDao.findById(distributor.getId()).get();

        assertTrue(distributor.getId() == foundDistributor.getId());
        assertTrue(distributor.getEmail() == foundDistributor.getEmail());
        assertTrue(distributor.getName() == foundDistributor.getName());
        assertTrue(distributor.getPhone() == foundDistributor.getPhone());

    }

    @Test
    public void findDistributorsByNameTest() {
        Distributor distributor = new Distributor("testDistributor", "test@email.com", "123456",
                "testAddress");
        Distributor distributor2 = new Distributor("testDistributor", "test@email.com", "123456",
                "testAddress");
        Distributor distributor3 = new Distributor("testDistributor", "test@email.com", "123456",
                "testAddress");
        Distributor distributor4 = new Distributor("distributor", "test@email.com", "123456",
                "testAddress");
        distributor = offerService.addDistributor(distributor);
        distributor2 = offerService.addDistributor(distributor2);
        distributor3 = offerService.addDistributor(distributor3);
        distributor4 = offerService.addDistributor(distributor4);


        List<Distributor> distributors = offerService.findDistributorsByName("test");
        assertTrue(distributors.size() == 3);
    }

    @Test
    public void getDistributorsTest() {
        Distributor distributor = new Distributor("testDistributor", "test@email.com", "123456",
                "testAddress");
        Distributor distributor2 = new Distributor("testDistributor", "test@email.com", "123456",
                "testAddress");
        Distributor distributor3 = new Distributor("testDistributor", "test@email.com", "123456",
                "testAddress");
        Distributor distributor4 = new Distributor("distributor", "test@email.com", "123456",
                "testAddress");
        distributor = offerService.addDistributor(distributor);
        distributor2 = offerService.addDistributor(distributor2);
        distributor3 = offerService.addDistributor(distributor3);
        distributor4 = offerService.addDistributor(distributor4);


        List<Distributor> distributors = offerService.getDistributors();
        assertTrue(distributors.size() == 4);
    }

    @Test
    public void getLastWeekOffersAndDistributorsTest() {

        Distributor distributor = new Distributor("testDistributor", "test@email.com", "123456",
                "testAddress");
        Distributor distributor2 = new Distributor("testDistributor", "test@email.com", "123456",
                "testAddress");

        Offer offer = createOffer();
        offer.setDate(LocalDate.now().minusDays(7));
        distributor.setOffer(offer);
        distributorDao.save(distributor);

        Offer offer2 = createOffer();
        offer2.setDate(LocalDate.now().minusDays(14));
        distributor2.setOffer(offer2);
        distributorDao.save(distributor2);

        List<Distributor> distributors = offerService.getLastWeekOffersAndDistributors();

        assertTrue(distributors.size() == 1);
        assertTrue(distributors.get(0).getId() == distributor.getId());
        assertTrue(distributors.get(0).getOffer().getId().equals(distributor.getOffer().getId()));
        assertTrue(distributors.get(0).getOffer().getOfferLines().size() == distributor.getOffer().getOfferLines().size());

    }

    @Test
    public void saveOffers() {
        Distributor distributor = new Distributor("testDistributor", "test@email.com", "123456",
                "testAddress");
        Distributor distributor2 = new Distributor("testDistributor", "test@email.com", "123456",
                "testAddress");

        Offer offer = createOffer();
        offer.setDate(LocalDate.now().minusDays(7));
        distributor.setOffer(offer);
        distributorDao.save(distributor);

        Offer offer2 = createOffer();
        offer2.setDate(LocalDate.now().minusDays(7));
        distributor2.setOffer(offer2);
        distributorDao.save(distributor2);
        List<Distributor> distributors = new ArrayList<>();

        distributors.add(distributor);
        distributors.add(distributor2);

        List<Distributor> distributorsSave = offerService.saveOffers(distributors);
        List<Distributor> distributorsFound = offerService.getLastWeekOffersAndDistributors();

        assertTrue(distributorsSave.size() == distributorsFound.size());
        assertTrue(distributorsSave.get(0).getId().equals(distributorsFound.get(0).getId()));
        assertTrue(distributorsSave.get(0).getOffer().getId().equals(distributorsFound.get(0).getOffer().getId()));
        assertTrue(distributorsSave.get(0).getOffer().getOfferLines().size() == distributorsFound.get(0).getOffer().getOfferLines().size());

    }

}
