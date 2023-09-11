package es.zocaminhoca.zocacontrol.backend.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Clientes {

    @Id
    @Column(columnDefinition = "CHAR(6)")
    private String codcliente;
    private String email;
    private String nombre;
    private String nombrecomercial;
    private LocalDate fechabaja;

    public Clientes() {
    }

    public Clientes(String codcliente, String email, String nombre, String nombrecomercial,
                    LocalDate fechabaja) {
        this.codcliente = codcliente;
        this.email = email;
        this.nombre = nombre;
        this.nombrecomercial = nombrecomercial;
        this.fechabaja = fechabaja;
    }

    public String getCodcliente() {
        return codcliente;
    }

    public void setCodcliente(String codcliente) {
        this.codcliente = codcliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombrecomercial() {
        return nombrecomercial;
    }

    public void setNombrecomercial(String nombrecomercial) {
        this.nombrecomercial = nombrecomercial;
    }

    public LocalDate getFechabaja() {
        return fechabaja;
    }

    public void setFechabaja(LocalDate fechabaja) {
        this.fechabaja = fechabaja;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Clientes clientes)) return false;
        return Objects.equals(getCodcliente(), clientes.getCodcliente()) && Objects.equals(getEmail(), clientes.getEmail()) && Objects.equals(getNombre(), clientes.getNombre()) && Objects.equals(getNombrecomercial(), clientes.getNombrecomercial()) && Objects.equals(getFechabaja(), clientes.getFechabaja());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodcliente(), getEmail(), getNombre(), getNombrecomercial(),
                getFechabaja());
    }
}
