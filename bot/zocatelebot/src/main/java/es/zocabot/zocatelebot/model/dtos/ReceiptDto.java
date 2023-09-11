package es.zocabot.zocatelebot.model.dtos;

import java.math.BigDecimal;

public class ReceiptDto {

    private Long id;
    private String clientName;
    private BillDto billDto;
    private String state;
    private BigDecimal price;

    public ReceiptDto() {
    }

    public ReceiptDto(Long id, String clientName, BillDto billDto, String state, BigDecimal price) {
        this.id = id;
        this.clientName = clientName;
        this.billDto = billDto;
        this.state = state;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public BillDto getBillDto() {
        return billDto;
    }

    public void setBillDto(BillDto billDto) {
        this.billDto = billDto;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
