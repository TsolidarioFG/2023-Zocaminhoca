package es.zocaminhoca.zocacontrol.backend.model.mappers;


import java.util.List;

public class WebOrder {

    private String billing_first_name;
    private String plain_orders_USER_billing_last_name;
    private List<WebOrderLine> products;
    private String static_field_2;
    private String customer_user;
    private String user_email;

    public WebOrder() {
    }

    public WebOrder(String billing_first_name, String plain_orders_USER_billing_last_name,
                    List<WebOrderLine> products, String static_field_2, String customer_user,
                    String user_email) {
        this.billing_first_name = billing_first_name;
        this.plain_orders_USER_billing_last_name = plain_orders_USER_billing_last_name;
        this.products = products;
        this.static_field_2 = static_field_2;
        this.customer_user = customer_user;
        this.user_email = user_email;
    }

    public String getBilling_first_name() {
        return billing_first_name;
    }

    public void setBilling_first_name(String billing_first_name) {
        this.billing_first_name = billing_first_name;
    }

    public String getPlain_orders_USER_billing_last_name() {
        return plain_orders_USER_billing_last_name;
    }

    public void setPlain_orders_USER_billing_last_name(String plain_orders_USER_billing_last_name) {
        this.plain_orders_USER_billing_last_name = plain_orders_USER_billing_last_name;
    }

    public List<WebOrderLine> getProducts() {
        return products;
    }

    public void setProducts(List<WebOrderLine> products) {
        this.products = products;
    }

    public String getStatic_field_2() {
        return static_field_2;
    }

    public void setStatic_field_2(String static_field_2) {
        this.static_field_2 = static_field_2;
    }

    public String getCustomer_user() {
        return customer_user;
    }

    public void setCustomer_user(String customer_user) {
        this.customer_user = customer_user;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
}
