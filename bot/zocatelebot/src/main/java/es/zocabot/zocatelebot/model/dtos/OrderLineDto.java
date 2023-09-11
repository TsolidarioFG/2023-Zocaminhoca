package es.zocabot.zocatelebot.model.dtos;

public class OrderLineDto {

    private String item;
    private int units;

    public OrderLineDto() {
    }

    public OrderLineDto(String item, int units) {
        this.item = item;
        this.units = units;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }
}
