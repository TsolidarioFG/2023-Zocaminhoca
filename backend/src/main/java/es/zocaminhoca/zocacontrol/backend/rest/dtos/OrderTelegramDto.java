package es.zocaminhoca.zocacontrol.backend.rest.dtos;

import java.util.List;

public class OrderTelegramDto {

    private String mondayDate;
    private String sundayDate;
    private String totalPrice;
    private List<OrderLineTelegramDto> orderLinesDtos;

    public OrderTelegramDto() {
    }

    public OrderTelegramDto(String mondayDate, String sundayDate, String totalPrice,
                            List<OrderLineTelegramDto> orderLinesDtos) {
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

    public List<OrderLineTelegramDto> getOrderLinesDtos() {
        return orderLinesDtos;
    }

    public void setOrderLinesDtos(List<OrderLineTelegramDto> orderLinesDtos) {
        this.orderLinesDtos = orderLinesDtos;
    }
}
