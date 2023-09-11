package es.zocaminhoca.zocacontrol.backend.model.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "lineasfacturascli")
public class BillLine {

    @Id
    @Column(name = "idlinea")
    private int id;
    @Column(name = "pvptotal")
    private BigDecimal totalPrice;
    @Column(name = "cantidad")
    private BigDecimal quantity;
    @Column(name = "pvpunitario")
    private BigDecimal unityPrice;
    @Column(name = "descripcion")
    private String description;

    public BillLine() {
    }

    public BillLine(int id, BigDecimal totalPrice, BigDecimal quantity, BigDecimal unityPrice,
                    String description) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.unityPrice = unityPrice;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnityPrice() {
        return unityPrice;
    }

    public void setUnityPrice(BigDecimal unityPrice) {
        this.unityPrice = unityPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
