package es.zocaminhoca.zocacontrol.backend.model.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "usuarias")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "telegramId")
    private Long telegramId;
    @Column(name = "webId")
    @Nullable
    private Long webId;
    @Nullable
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCliente", columnDefinition = "CHAR(6)")
    private Clientes client;
    @Column(name = "nombre")
    private String firstName;
    @Column(name = "apellidos")
    private String lastName;
    @Column(name = "nombreUsuaria")
    private String userName;
    @Column(name = "webEmail")
    private String webEmail;

    public User() {
    }

    public User(long id, long telegramId, long webId, Clientes client, String firstName,
                String lastName, String userName, String webEmail) {
        this.id = id;
        this.telegramId = telegramId;
        this.webId = webId;
        this.client = client;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.webEmail = webEmail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(Long telegramId) {
        this.telegramId = telegramId;
    }

    public Long getWebId() {
        return webId;
    }

    public void setWebId(Long webId) {
        this.webId = webId;
    }

    public Clientes getClient() {
        return client;
    }

    public void setClient(Clientes client) {
        this.client = client;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWebEmail() {
        return webEmail;
    }

    public void setWebEmail(String webEmail) {
        this.webEmail = webEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return getId() == user.getId() && Objects.equals(getTelegramId(), user.getTelegramId()) && Objects.equals(getWebId(), user.getWebId()) && Objects.equals(getFirstName(), user.getFirstName()) && Objects.equals(getLastName(), user.getLastName()) && Objects.equals(getUserName(), user.getUserName()) && Objects.equals(getWebEmail(), user.getWebEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTelegramId(), getWebId(), getFirstName(), getLastName(),
                getUserName(), getWebEmail());
    }
}
