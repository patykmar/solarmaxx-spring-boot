package cz.patyk.solarmaxx.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;

@Configuration
public class RelayTypeConfig {

    @Bean
    public PageRequest getPageRequest() {
        return PageRequest.of(0, 10);
    }

}
