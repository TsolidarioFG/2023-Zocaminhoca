package es.zocaminhoca.zocacontrol.backend.rest.dtos;

import java.math.BigDecimal;

public class OrderLineTelegramDto {

    private String item;
    private BigDecimal units;

    public OrderLineTelegramDto() {
    }

    public OrderLineTelegramDto(String item, BigDecimal units) {
        this.item = item;
        this.units = units;
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
}
