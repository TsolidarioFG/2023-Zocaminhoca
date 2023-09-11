package es.zocaminhoca.zocacontrol.backend.rest.dtos;

import es.zocaminhoca.zocacontrol.backend.model.entities.Offer;
import es.zocaminhoca.zocacontrol.backend.model.entities.OfferLine;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OfferConversor {

    public final static OfferLineDto toOfferLineDto(OfferLine offerLine) {
        return new OfferLineDto(offerLine.getId(), offerLine.getPrice(), offerLine.getQuantity(),
                offerLine.getProduct(), offerLine.getProductCode());
    }

    public final static List<OfferLineDto> toOfferLineDtos(List<OfferLine> offerLines) {
        return offerLines.stream().map(o -> toOfferLineDto(o)).collect(Collectors.toList());
    }

    public final static OfferLine toOfferLine(OfferLineDto offerLineDto) {
        return new OfferLine(offerLineDto.getId(), offerLineDto.getPrice(),
                offerLineDto.getQuantity(), offerLineDto.getProduct(),
                offerLineDto.getProductCode());
    }

    public final static List<OfferLine> toOfferLines(List<OfferLineDto> offerLineDtos) {
        return offerLineDtos.stream().map(o -> toOfferLine(o)).collect(Collectors.toList());
    }

    public final static OfferDto toOfferDto(Offer offer) {

        List<OfferLineDto> offerLineDtos = null;
        if (!Objects.isNull(offer.getOfferLines())) {
            offerLineDtos = toOfferLineDtos(offer.getOfferLines());
        }

        return new OfferDto(offer.getId(), offerLineDtos);

    }

    public final static Offer toOffer(OfferDto offerDto) {

        List<OfferLine> offerLines = toOfferLines(offerDto.getOfferLines());

        return new Offer(offerDto.getId(), offerLines);
    }
}
