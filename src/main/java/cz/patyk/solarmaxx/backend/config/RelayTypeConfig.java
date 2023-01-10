package cz.patyk.solarmaxx.backend.config;

import cz.patyk.solarmaxx.backend.dto.relay.type.RelayTypeConstants;
import cz.patyk.solarmaxx.backend.dto.relay.type.url.RelayTypeUrlPatternDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;

@Configuration
public class RelayTypeConfig {

    @Bean
    public RelayTypeUrlPatternDto relayTypeUrlPattern() {
        return RelayTypeUrlPatternDto.builder()
                .ip(RelayTypeConstants.RELAY_TYPE_URL_PATTERN_IP)
                .id(RelayTypeConstants.RELAY_TYPE_URL_PATTERN_ID)
                .port(RelayTypeConstants.RELAY_TYPE_URL_PATTERN_PORT)
                .toggle(RelayTypeConstants.RELAY_TYPE_URL_PATTERN_TOGGLE)
                .build();
    }

    @Bean
    public PageRequest getPageRequest() {
        return PageRequest.of(0, 10);
    }

}
