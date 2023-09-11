package es.zocabot.zocatelebot.model.dtos;

import java.util.List;

public class OrderDto {
    private String mondayDate;
    private String sundayDate;
    private String totalPrice;
    private List<OrderLineDto> orderLinesDtos;

    public OrderDto() {
    }

    public OrderDto(String mondayDate, String sundayDate, String totalPrice,
                    List<OrderLineDto> orderLinesDtos) {
        this.mondayDate = mondayDate;
        this.sundayDate = sundayDate;
        this.totalPrice = totalPrice;
        this.orderLinesDtos = orderLinesDtos;
    }

    public String getMondayDate() {
        return mondayDate;
    }

    public void setMondayDate(String mondayDate) {
        this.mondayDate = mondayDate;
    }

    public String getSundayDate() {
        return sundayDate;
    }

    public void setSundayDate(String sundayDate) {
        this.sundayDate = sundayDate;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderLineDto> getOrderLinesDtos() {
        return orderLinesDtos;
    }

    public void setOrderLinesDtos(List<OrderLineDto> orderLinesDtos) {
        this.orderLinesDtos = orderLinesDtos;
    }
}
