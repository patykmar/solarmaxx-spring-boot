package cz.patyk.solarmaxx.backend.mapper.relay.type.url;

import cz.patyk.solarmaxx.backend.config.DeviceTypeUrlPattern;
import cz.patyk.solarmaxx.backend.dto.relay.type.url.parameter.StatusUrlParameter;
import cz.patyk.solarmaxx.backend.dto.relay.type.url.parameter.ToggleUrlParameter;
import org.junit.jupiter.api.BeforeEach;


abstract class AbstractRelayTypeUrlMapperTest {
    public static final String RELAY_IP = "1.2.3.4";
    public static final Short RELAY_PORT = (short) 80;
    public static final Byte SPECIFIC_ID = 8;
    public static final Short SPECIFIC_PORT = 8888;
    protected StatusUrlParameter statusUrlParameter;
    protected ToggleUrlParameter toggleUrlParameter;
    protected DeviceTypeUrlPattern deviceTypeUrlPattern;

    @BeforeEach
    void setUp() {
        deviceTypeUrlPattern = new DeviceTypeUrlPattern();
        deviceTypeUrlPattern.setId("%ID");
        deviceTypeUrlPattern.setIp("%IP");
        deviceTypeUrlPattern.setPort("%PORT");
        deviceTypeUrlPattern.setToggle("%TOGGLE");

        statusUrlParameter = StatusUrlParameter.builder()
                .ip(RELAY_IP)
                .port(RELAY_PORT)
                .build();

        toggleUrlParameter = ToggleUrlParameter.builder()
                .ip(RELAY_IP)
                .port(RELAY_PORT)
                .build();
    }
}