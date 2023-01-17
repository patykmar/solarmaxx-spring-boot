package cz.patyk.solarmaxx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SolarmaxxApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolarmaxxApplication.class, args);
    }

}
