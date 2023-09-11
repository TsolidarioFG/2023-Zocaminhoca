package es.zocaminhoca.zocacontrol.backend.model.mappers;

import java.math.BigDecimal;

public class WebOrderLine {
    private String name;
    private float item_price;
    private BigDecimal qty;

    public WebOrderLine() {
    }

    public WebOrderLine(String name, float item_price, BigDecimal qty) {
        this.name = name;
        this.item_price = item_price;
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getItem_price() {
        return item_price;
    }

    public void setItem_price(float item_price) {
        this.item_price = item_price;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }
}
