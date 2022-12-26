package cz.patyk.solarmaxx.backend.mapper.relay.type.url;

import cz.patyk.solarmaxx.backend.mapper.relay.TestRelayConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ShellyProUrlMapperTest extends AbstractRelayTypeUrlMapperTest {
    private ShellyProUrlMapper shellyProUrlMapper;

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        statusUrlParameter.setTemplate(TestRelayConstants.URL_STATUS_SHELLY_PRO);
        toggleUrlParameter.setTemplate(TestRelayConstants.URL_TOGGLE_SHELLY_PRO);
        shellyProUrlMapper = new ShellyProUrlMapper(deviceTypeUrlPattern);
    }

    @Test
    void toRealUrlStatusNotSpecifyOutputId(){
        String expected = String.format("http://%s:80/rpc/Switch.GetStatus?id=0", RELAY_IP);
        Assertions.assertEquals(expected, shellyProUrlMapper.toRealUrlStatus(statusUrlParameter));
    }

    @Test
    void toRealUrlStatusSpecifyOutputId(){
        String expected = String.format("http://%s:80/rpc/Switch.GetStatus?id=%d", RELAY_IP, SPECIFIC_ID);
        statusUrlParameter.setOutputId(SPECIFIC_ID);
        Assertions.assertEquals(expected, shellyProUrlMapper.toRealUrlStatus(statusUrlParameter));
    }

    @Test
    void toRealUrlStatusSpecifyOutputIdAndPort(){
        String expected = String.format("http://%s:%d/rpc/Switch.GetStatus?id=%d", RELAY_IP, SPECIFIC_PORT, SPECIFIC_ID);
        statusUrlParameter.setOutputId(SPECIFIC_ID);
        statusUrlParameter.setPort(SPECIFIC_PORT);
        Assertions.assertEquals(expected, shellyProUrlMapper.toRealUrlStatus(statusUrlParameter));
    }

    @Test
    void toRealUrlToggleNotSpecifyOutputId(){
        String expected = String.format("http://%s:80/rpc/Switch.Toggle?id=0", RELAY_IP);
        Assertions.assertEquals(expected, shellyProUrlMapper.toRealUrlToggle(toggleUrlParameter));
    }

    @Test
    void toRealUrlToggleSpecifyOutputId(){
        String expected = String.format("http://%s:80/rpc/Switch.Toggle?id=%d", RELAY_IP, SPECIFIC_ID);
        toggleUrlParameter.setOutputId(SPECIFIC_ID);
        Assertions.assertEquals(expected, shellyProUrlMapper.toRealUrlToggle(toggleUrlParameter));
    }

    @Test
    void toRealUrlToggleSpecifyOutputIdAndPort(){
        String expected = String.format("http://%s:%d/rpc/Switch.Toggle?id=%d", RELAY_IP, SPECIFIC_PORT, SPECIFIC_ID);
        toggleUrlParameter.setOutputId(SPECIFIC_ID);
        toggleUrlParameter.setPort(SPECIFIC_PORT);
        Assertions.assertEquals(expected, shellyProUrlMapper.toRealUrlToggle(toggleUrlParameter));
    }

}