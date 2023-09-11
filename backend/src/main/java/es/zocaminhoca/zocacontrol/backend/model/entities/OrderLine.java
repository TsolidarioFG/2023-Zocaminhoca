package es.zocaminhoca.zocacontrol.backend.model.entities;


import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "lineaspedido")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "producto")
    private String product;
    @Column(name = "unidades")
    private BigDecimal units;
    @Column(name = "precioUnidad")
    private BigDecimal price;

    public OrderLine() {
    }

    public OrderLine(String product, BigDecimal units, BigDecimal price) {
        this.product = product;
        this.units = units;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public BigDecimal getUnits() {
        return units;
    }

    public void setUnits(BigDecimal units) {
        this.units = units;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
