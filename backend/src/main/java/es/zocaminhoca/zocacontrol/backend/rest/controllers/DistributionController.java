package es.zocaminhoca.zocacontrol.backend.rest.controllers;

import es.zocaminhoca.zocacontrol.backend.model.services.OfferService;
import es.zocaminhoca.zocacontrol.backend.rest.dtos.DistributorConversor;
import es.zocaminhoca.zocacontrol.backend.rest.dtos.DistributorDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/distribution")
public class DistributionController {

    @Autowired
    private OfferService offerService;

    @Operation(
            summary = "Devuelve la lista de todos los productoras"
    )
    @GetMapping("/distributors/all")
    public List<DistributorDto> getDistributors() {
        return DistributorConversor.toDistributorDtos(offerService.getDistributors());
    }

    @Operation(
            summary = "Busca productoras por nombre"
    )
    @GetMapping("/distributors")
    public List<DistributorDto> findDistributorByName(@RequestParam(required = true) String queryName) {
        return DistributorConversor.toDistributorDtos(offerService.findDistributorsByName(queryName));
    }

    @Operation(
            summary = "Añade una nueva productora"
    )
    @PostMapping("/distributors")
    public DistributorDto addNewDistributor(@RequestBody DistributorDto distributorDto) {
        return DistributorConversor.toDistributorDto(offerService.addDistributor(DistributorConversor.toDistributor(distributorDto)));
    }

    @Operation(
            summary = "Guarda la lista semanas de ofertas según productora"
    )
    @PostMapping("/offers")
    public List<DistributorDto> addOffers(@RequestBody List<DistributorDto> distributorDtos) {
        return DistributorConversor.toDistributorDtos(offerService.saveOffers(DistributorConversor.toDistributors(distributorDtos)));
    }

    @Operation(
            summary = "Devuelve la lista de ofertas de la semana anterior"
    )
    @GetMapping("/offers")
    public List<DistributorDto> getWeeklyOffers() {
        return DistributorConversor.toDistributorDtos(offerService.getLastWeekOffersAndDistributors());
    }

    @Operation(
            summary = "Devuelve la lista de ofertas guardadas para esta semana"
    )
    @GetMapping("/offers/saved")
    public List<DistributorDto> getSavedOffers() {
        return DistributorConversor.toDistributorDtos(offerService.getSavedOffers());
    }
}
