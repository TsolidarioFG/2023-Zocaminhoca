package es.zocaminhoca.zocacontrol.backend.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "archivo.transferencias")
public class TransfersPathConfiguration {

    private String folderPath;

    public TransfersPathConfiguration() {
    }

    public TransfersPathConfiguration(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }
}
