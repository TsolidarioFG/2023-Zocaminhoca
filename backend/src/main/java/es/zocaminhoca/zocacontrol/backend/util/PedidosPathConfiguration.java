package es.zocaminhoca.zocacontrol.backend.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "archivo.pedidos")
public class PedidosPathConfiguration {

    private String folderPath;

    public PedidosPathConfiguration() {
    }

    public PedidosPathConfiguration(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }
}
