package es.zocaminhoca.zocacontrol.backend.rest.dtos;

import es.zocaminhoca.zocacontrol.backend.model.daos.DistributorDao;
import es.zocaminhoca.zocacontrol.backend.model.entities.Distributor;
import es.zocaminhoca.zocacontrol.backend.model.entities.Offer;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DistributorConversor {

    public final static Distributor toDistributor(DistributorDto distributorDto) {

        Offer offer = null;
        if (!Objects.isNull(distributorDto.getOfferDto())) {
            offer = OfferConversor.toOffer(distributorDto.getOfferDto());
        }

        return new Distributor(distributorDto.getId(), distributorDto.getName(),
                distributorDto.getEmail(),
                distributorDto.getPhone(), distributorDto.getAddress(), offer);
    }

    public final static List<Distributor> toDistributors(List<DistributorDto> distributorDtos) {
        return distributorDtos.stream().map(d -> toDistributor(d)).collect(Collectors.toList());
    }


    public final static DistributorDto toDistributorDto(Distributor distributor) {
        OfferDto offerDto = null;
        if (!Objects.isNull(distributor.getOffer())) {
            offerDto = OfferConversor.toOfferDto(distributor.getOffer());
        }

        return new DistributorDto(distributor.getId(), distributor.getName(),
                distributor.getEmail(), distributor.getPhone(), distributor.getAddress(), offerDto);
    }

    public final static List<DistributorDto> toDistributorDtos(List<Distributor> distributors) {
        return distributors.stream().map(d -> toDistributorDto(d)).collect(Collectors.toList());
    }
}
