package es.zocaminhoca.zocacontrol.backend.model.exceptions;

public class UserTelegramIdAlreadyExists extends Exception {

    private String key;

    public UserTelegramIdAlreadyExists(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
