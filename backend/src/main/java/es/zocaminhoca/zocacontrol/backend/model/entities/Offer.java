package es.zocaminhoca.zocacontrol.backend.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ofertas")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fecha")
    private LocalDate date;
    @Column(name = "semanaDelAno")
    private Integer weekOfYear;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idOferta")
    private List<OfferLine> offerLines;

    public Offer() {
    }

    public Offer(Long id, List<OfferLine> offerLines) {
        this.id = id;
        this.offerLines = offerLines;
    }

    public Offer(long id, LocalDate date, Integer weekOfYear,
                 List<OfferLine> offerLines) {
        this.id = id;
        this.date = date;
        this.weekOfYear = weekOfYear;
        this.offerLines = offerLines;
    }

    public Offer(LocalDate date, Integer weekOfYear,
                 List<OfferLine> offerLines) {
        this.id = id;
        this.date = date;
        this.weekOfYear = weekOfYear;
        this.offerLines = offerLines;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getWeekOfYear() {
        return weekOfYear;
    }

    public void setWeekOfYear(Integer weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    public List<OfferLine> getOfferLines() {
        return offerLines;
    }

    public void setOfferLines(List<OfferLine> offerLines) {
        this.offerLines = offerLines;
    }
}
