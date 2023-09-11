package es.zocaminhoca.zocacontrol.backend.util;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${http://localhost:8080}")
    private String serverUrl;

    @Bean
    public OpenAPI zocaOpenAPI() {
        Server server = new Server();
        server.setUrl(serverUrl);
        server.setDescription("URL del servidor");

        Info info = new Info()
                .title("ZocaControl API")
                .version("1.0")
                .description("API de ZocaControl. Expone las funcionalidades de gestión de la " +
                        "cooperativa y de conexión al Bot.");

        return new OpenAPI().info(info).servers(List.of(server));
    }

}
