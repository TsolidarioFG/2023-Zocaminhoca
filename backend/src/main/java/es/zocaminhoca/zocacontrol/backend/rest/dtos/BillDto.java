package es.zocaminhoca.zocacontrol.backend.rest.dtos;

import java.math.BigDecimal;
import java.util.List;

public class BillDto {

    private Long id;
    private String time;
    private String date;
    private List<BillLineDto> billLinesDtos;

    public BillDto() {
    }

    public BillDto(Long id, String time, String date, List<BillLineDto> billLinesDtos) {
        this.id = id;
        this.time = time;
        this.date = date;
        this.billLinesDtos = billLinesDtos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<BillLineDto> getBillLinesDtos() {
        return billLinesDtos;
    }

    public void setBillLinesDtos(List<BillLineDto> billLinesDtos) {
        this.billLinesDtos = billLinesDtos;
    }
}
