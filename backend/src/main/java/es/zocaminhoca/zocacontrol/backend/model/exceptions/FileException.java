package es.zocaminhoca.zocacontrol.backend.model.exceptions;

public class FileException extends Exception {

    private String path;

    private String message;

    public FileException(String path, String message) {
        this.message = message;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
