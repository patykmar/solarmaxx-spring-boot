package cz.patyk.solarmaxx.backend.mapper.relay.type.url;

import cz.patyk.solarmaxx.backend.dto.relay.type.RelayTypeConstants;
import cz.patyk.solarmaxx.backend.dto.relay.type.url.RelayTypeUrlPatternDto;
import cz.patyk.solarmaxx.backend.dto.relay.type.url.parameter.StatusUrlParameter;
import cz.patyk.solarmaxx.backend.dto.relay.type.url.parameter.ToggleUrlParameter;
import cz.patyk.solarmaxx.backend.mapper.relay.TestRelayConstants;
import org.junit.jupiter.api.BeforeEach;


abstract class AbstractRelayTypeUrlMapperTest {
    public static final Byte SPECIFIC_ID = 8;
    public static final Short SPECIFIC_PORT = 8888;
    protected StatusUrlParameter statusUrlParameter;
    protected ToggleUrlParameter toggleUrlParameter;
    protected RelayTypeUrlPatternDto relayTypeUrlPatternDto;

    @BeforeEach
    void setUp() {
        relayTypeUrlPatternDto = RelayTypeUrlPatternDto.builder()
                .id(RelayTypeConstants.RELAY_TYPE_URL_PATTERN_ID)
                .ip(RelayTypeConstants.RELAY_TYPE_URL_PATTERN_IP)
                .port(RelayTypeConstants.RELAY_TYPE_URL_PATTERN_PORT)
                .toggle(RelayTypeConstants.RELAY_TYPE_URL_PATTERN_TOGGLE)
                .build();

        statusUrlParameter = StatusUrlParameter.builder()
                .ipAddress(TestRelayConstants.RELAY_IP)
                .port(TestRelayConstants.RELAY_PORT)
                .build();

        toggleUrlParameter = ToggleUrlParameter.builder()
                .ipAddress(TestRelayConstants.RELAY_IP)
                .port(TestRelayConstants.RELAY_PORT)
                .build();
    }
}