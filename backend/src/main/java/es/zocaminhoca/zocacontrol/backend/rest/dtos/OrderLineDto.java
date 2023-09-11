package es.zocaminhoca.zocacontrol.backend.rest.dtos;

import java.math.BigDecimal;

public class OrderLineDto {

    private Long id;
    private String item;
    private BigDecimal units;
    private BigDecimal unitPrice;

    public OrderLineDto() {
    }

    public OrderLineDto(Long id, String item, BigDecimal units, BigDecimal unitPrice) {
        this.id = id;
        this.item = item;
        this.units = units;
        this.unitPrice = unitPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public BigDecimal getUnits() {
        return units;
    }

    public void setUnits(BigDecimal units) {
        this.units = units;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
