package es.zocaminhoca.zocacontrol.backend.model.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "pedidos")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuaria")
    private User user;
    @Column(name = "nombre")
    private String name;
    @Column(name = "apellidos")
    private String lastName;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idPedido")
    private List<OrderLine> orderLines;
    @Column(name = "webId")
    private Long webId;
    @Column(name = "fecha")
    private LocalDate date;
    @Column(name = "semanaDelAno")
    private int weekOfYear;
    @Column(name = "precioTotal")
    private BigDecimal totalPrice;

    public Order() {
    }

    public Order(String email, User user, String name, String lastName,
                 List<OrderLine> orderLines, Long webId, LocalDate date, int weekOfYear,
                 BigDecimal totalPrice) {
        this.email = email;
        this.user = user;
        this.name = name;
        this.lastName = lastName;
        this.orderLines = orderLines;
        this.totalPrice = totalPrice;
        this.webId = webId;
        this.date = date;
        this.weekOfYear = weekOfYear;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public Long getWebId() {
        return webId;
    }

    public void setWebId(Long webId) {
        this.webId = webId;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getWeekOfYear() {
        return weekOfYear;
    }

    public void setWeekOfYear(int weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return getWeekOfYear() == order.getWeekOfYear() && Objects.equals(getId(), order.getId()) && Objects.equals(getEmail(), order.getEmail()) && Objects.equals(getUser(), order.getUser()) && Objects.equals(getName(), order.getName()) && Objects.equals(getLastName(), order.getLastName()) && Objects.equals(getOrderLines(), order.getOrderLines()) && Objects.equals(getWebId(), order.getWebId()) && Objects.equals(getDate(), order.getDate()) && Objects.equals(getTotalPrice(), order.getTotalPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getUser(), getName(), getLastName(),
                getOrderLines(), getWebId(), getDate(), getWeekOfYear(), getTotalPrice());
    }
}
