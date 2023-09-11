package es.zocaminhoca.zocacontrol.backend.model.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "facturascli")
public class Bill {

    @Id
    @Column(name = "idfactura")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codcliente")
    private Clientes client;
    @Column(name = "totaleuros")
    private BigDecimal totalPrice;
    @Column(name = "hora")
    private LocalTime time;
    @Column(name = "fecha")
    private LocalDate date;
    @Column(name = "codapago")
    private String codpago;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "idfactura")
    private List<BillLine> billLines;

    public Bill() {
    }

    public Bill(Long id, Clientes client, BigDecimal totalPrice, LocalTime time, LocalDate date,
                String codpago, List<BillLine> billLines) {
        this.id = id;
        this.client = client;
        this.totalPrice = totalPrice;
        this.time = time;
        this.date = date;
        this.codpago = codpago;
        this.billLines = billLines;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Clientes getClient() {
        return client;
    }

    public void setClient(Clientes client) {
        this.client = client;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCodpago() {
        return codpago;
    }

    public void setCodpago(String codpago) {
        this.codpago = codpago;
    }

    public List<BillLine> getBillLines() {
        return billLines;
    }

    public void setBillLines(List<BillLine> billLines) {
        this.billLines = billLines;
    }
}
