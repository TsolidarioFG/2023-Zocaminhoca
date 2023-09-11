package es.zocaminhoca.zocacontrol.backend.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class InstanceNotFoundException extends Exception {

    private String instanceClass;
    private String key;

    public InstanceNotFoundException(String instanceClass, String key) {
        this.instanceClass = instanceClass;
        this.key = key;
    }

    public String getInstanceClass() {
        return instanceClass;
    }

    public String getKey() {
        return key;
    }
}
