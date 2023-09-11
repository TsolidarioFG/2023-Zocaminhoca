package es.zocaminhoca.zocacontrol.backend.rest.dtos;

import java.math.BigDecimal;
import java.util.List;

public class OrderDto {

    private Long id;
    private String email;
    private UserDto userDto;
    private String name;
    private String lastName;
    private Long webId;
    private List<OrderLineDto> orderLines;
    private BigDecimal totalPrice;

    public OrderDto() {
    }

    public OrderDto(Long id, String email, UserDto userDto, String name, String lastName,
                    Long webId, List<OrderLineDto> orderLines, BigDecimal totalPrice) {
        this.id = id;
        this.email = email;
        this.userDto = userDto;
        this.name = name;
        this.lastName = lastName;
        this.webId = webId;
        this.orderLines = orderLines;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getWebId() {
        return webId;
    }

    public void setWebId(Long webId) {
        this.webId = webId;
    }

    public List<OrderLineDto> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLineDto> orderLines) {
        this.orderLines = orderLines;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
