package cz.patyk.solarmaxx.backend.config;

import cz.patyk.solarmaxx.backend.dto.relay.type.RelayTypeConstants;
import cz.patyk.solarmaxx.backend.dto.relay.type.url.RelayTypeUrlPattern;
import org.springframework.context.annotation.Bean;

public class RelayTypeConfig {

    @Bean
    public RelayTypeUrlPattern relayTypeUrlPattern() {
        return RelayTypeUrlPattern.builder()
                .ip(RelayTypeConstants.RELAY_TYPE_URL_PATTERN_IP)
                .id(RelayTypeConstants.RELAY_TYPE_URL_PATTERN_ID)
                .port(RelayTypeConstants.RELAY_TYPE_URL_PATTERN_PORT)
                .toggle(RelayTypeConstants.RELAY_TYPE_URL_PATTERN_TOGGLE)
                .build();
    }
}
