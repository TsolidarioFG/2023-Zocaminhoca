package es.zocaminhoca.zocacontrol.backend.rest.dtos;

public class ErrorsDto {

    private String globalError;
    private String key;
    private String instanceClass;

    public ErrorsDto(String globalError, String key, String instanceClass) {
        this.globalError = globalError;
        this.key = key;
        this.instanceClass = instanceClass;
    }

    public String getGlobalError() {
        return globalError;
    }

    public void setGlobalError(String globalError) {
        this.globalError = globalError;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getInstanceClass() {
        return instanceClass;
    }

    public void setInstanceClass(String instanceClass) {
        this.instanceClass = instanceClass;
    }
}
