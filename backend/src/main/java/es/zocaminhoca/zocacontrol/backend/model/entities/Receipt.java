package es.zocaminhoca.zocacontrol.backend.model.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "reciboscli")
public class Receipt {

    @Id
    @Column(name = "idrecibo")
    private Long id;
    @Column(name = "nombrecliente")
    private String clientName;
    @OneToOne(fetch = FetchType.LAZY)
    //@PrimaryKeyJoinColumn(name = "idfactura")
    @JoinColumn(name = "idfactura")
    private Bill bill;
    @Column(name = "estado", columnDefinition = "CHAR(20)")
    private String state;
    @Column(name = "fecha")
    private LocalDate date;
    @ManyToOne(fetch = FetchType.LAZY)
    //@PrimaryKeyJoinColumn(name = "codcliente")
    @JoinColumn(name = "codcliente", columnDefinition = "CHAR(6)")
    private Clientes client;
    @Column(name = "importe")
    private BigDecimal price;

    public Receipt() {
    }

    public Receipt(Long id, String clientName, Bill bill, String state, LocalDate date,
                   Clientes client, BigDecimal price) {
        this.id = id;
        this.clientName = clientName;
        this.bill = bill;
        this.state = state;
        this.date = date;
        this.client = client;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Clientes getClient() {
        return client;
    }

    public void setClient(Clientes client) {
        this.client = client;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
