package es.zocaminhoca.zocacontrol.backend.rest.dtos;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public class JobDto {

    private long id;
    private String name;
    private String description;
    private LocalDate doneDate;
    private boolean done;

    public JobDto() {
    }

    public JobDto(long id, String name, String description, LocalDate doneDate, boolean done) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.doneDate = doneDate;
        this.done = done;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public boolean getDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public LocalDate getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(LocalDate doneDate) {
        this.doneDate = doneDate;
    }
}
