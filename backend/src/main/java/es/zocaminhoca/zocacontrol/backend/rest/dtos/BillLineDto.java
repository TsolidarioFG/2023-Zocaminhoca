package es.zocaminhoca.zocacontrol.backend.rest.dtos;

import java.math.BigDecimal;

public class BillLineDto {

    private String description;
    private BigDecimal unityPrice;
    private BigDecimal totalPrice;
    private BigDecimal quantity;

    public BillLineDto() {
    }

    public BillLineDto(String description, BigDecimal unityPrice, BigDecimal totalPrice,
                       BigDecimal quantity) {
        this.description = description;
        this.unityPrice = unityPrice;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getUnityPrice() {
        return unityPrice;
    }

    public void setUnityPrice(BigDecimal unityPrice) {
        this.unityPrice = unityPrice;
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
}
