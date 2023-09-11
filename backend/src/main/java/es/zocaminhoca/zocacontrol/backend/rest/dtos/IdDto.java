package es.zocaminhoca.zocacontrol.backend.rest.dtos;

public class IdDto {

    private long id;

    public IdDto(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
