package es.zocaminhoca.zocacontrol.backend.rest.dtos;

import java.util.List;

public class OfferDto {

    private Long id;
    private List<OfferLineDto> offerLines;

    public OfferDto() {
    }

    public OfferDto(Long id, List<OfferLineDto> offerLines) {
        this.id = id;
        this.offerLines = offerLines;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OfferLineDto> getOfferLines() {
        return offerLines;
    }

    public void setOfferLines(List<OfferLineDto> offerLines) {
        this.offerLines = offerLines;
    }
}
