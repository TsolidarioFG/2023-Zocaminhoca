package es.zocaminhoca.zocacontrol.backend.model.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "lineasoferta")
public class OfferLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "precioUnitario")
    private BigDecimal price;
    @Column(name = "cantidad")
    private BigDecimal quantity;
    @Column(name = "producto")
    private String product;
    @Column(name = "codigoProducto")
    private String productCode;

    public OfferLine() {
    }

    public OfferLine(Long id, BigDecimal price, BigDecimal quantity, String product,
                     String productCode) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.product = product;
        this.productCode = productCode;
    }

    public OfferLine(BigDecimal price, BigDecimal quantity, String product,
                     String productCode) {
        this.price = price;
        this.quantity = quantity;
        this.product = product;
        this.productCode = productCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
