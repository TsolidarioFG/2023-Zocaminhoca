package es.zocaminhoca.zocacontrol.backend.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tareas")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    private String name;
    @Column(name = "descripcion")
    private String description;
    @Column(name = "fechaRealizacion")
    private LocalDate doneDate;
    @Column(name = "semanaDelAno")
    private int weekOfYearDone;

    public Job() {
    }

    public Job(Long id, String name, String description, LocalDate doneDate, int weekOfYearDone) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.doneDate = doneDate;
        this.weekOfYearDone = weekOfYearDone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(LocalDate doneDate) {
        this.doneDate = doneDate;
    }

    public int getWeekOfYearDone() {
        return weekOfYearDone;
    }

    public void setWeekOfYearDone(int weekOfYearDone) {
        this.weekOfYearDone = weekOfYearDone;
    }
}
