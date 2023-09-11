package es.zocabot.zocatelebot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "model.connection")
public class ModelConnectionConfiguration {

    private String uri;

    public ModelConnectionConfiguration() {
    }

    public ModelConnectionConfiguration(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
