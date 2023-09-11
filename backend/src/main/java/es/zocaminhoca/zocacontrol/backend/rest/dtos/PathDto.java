package es.zocaminhoca.zocacontrol.backend.rest.dtos;

import java.nio.file.Path;

public class PathDto {

    private String path;

    public PathDto() {
    }

    public PathDto(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
