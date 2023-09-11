package es.zocaminhoca.zocacontrol.backend.rest.dtos;

import java.math.BigDecimal;

public class OfferLineDto {

    private Long id;
    private BigDecimal price;
    private BigDecimal quantity;
    private String product;
    private String productCode;

    public OfferLineDto() {
    }

    public OfferLineDto(Long id, BigDecimal price, BigDecimal quantity, String product,
                        String productCode) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.product = product;
        this.productCode = productCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
