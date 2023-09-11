package es.zocaminhoca.zocacontrol.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class ZocacontrolApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZocacontrolApplication.class, args);
    }

}
