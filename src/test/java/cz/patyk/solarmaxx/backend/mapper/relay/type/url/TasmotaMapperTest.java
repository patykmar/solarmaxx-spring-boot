package cz.patyk.solarmaxx.backend.mapper.relay.type.url;

import cz.patyk.solarmaxx.backend.mapper.relay.TestRelayConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TasmotaMapperTest extends AbstractRelayTypeUrlMapperTest {
    private TasmotaUrlMapper tasmotaUrlMapper;

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        statusUrlParameter.setTemplate(TestRelayConstants.URL_STATUS_TASMOTA);
        toggleUrlParameter.setTemplate(TestRelayConstants.URL_TOGGLE_TASMOTA);
        tasmotaUrlMapper = new TasmotaUrlMapper(deviceTypeUrlPattern);
    }

    @Test
    void toRealUrlStatusNotSpecifyOutputId() {
        String expected = "http://" + RELAY_IP + ":80/cm?cmnd=Power1%20STATUS";
        Assertions.assertEquals(expected, tasmotaUrlMapper.toRealUrlStatus(statusUrlParameter));
    }

    @Test
    void toRealUrlStatusSpecifyOutputId() {
        String expected = "http://" + RELAY_IP + ":80/cm?cmnd=Power" + SPECIFIC_ID + "%20STATUS";
        statusUrlParameter.setOutputId(SPECIFIC_ID);
        Assertions.assertEquals(expected, tasmotaUrlMapper.toRealUrlStatus(statusUrlParameter));
    }

    @Test
    void toRealUrlStatusSpecifyOutputIdAndPort() {
        String expected = "http://" + RELAY_IP + ":" + SPECIFIC_PORT + "/cm?cmnd=Power" + SPECIFIC_ID + "%20STATUS";
        statusUrlParameter.setOutputId(SPECIFIC_ID);
        statusUrlParameter.setPort(SPECIFIC_PORT);
        Assertions.assertEquals(expected, tasmotaUrlMapper.toRealUrlStatus(statusUrlParameter));
    }

    @Test
    void toRealUrlToggleNotSpecifyOutputId() {
        String expected = "http://" + RELAY_IP + ":80/cm?cmnd=Power1%20%TOGGLE";
        Assertions.assertEquals(expected, tasmotaUrlMapper.toRealUrlToggle(toggleUrlParameter));
    }

    @Test
    void toRealUrlToggleSpecifyOutputId() {
        String expected = "http://" + RELAY_IP + ":80/cm?cmnd=Power" + SPECIFIC_ID + "%20%TOGGLE";
        toggleUrlParameter.setOutputId(SPECIFIC_ID);
        Assertions.assertEquals(expected, tasmotaUrlMapper.toRealUrlToggle(toggleUrlParameter));
    }

    @Test
    void toRealUrlToggleSpecifyOutputIdAndPort() {
        String expected = "http://" + RELAY_IP + ":" + SPECIFIC_PORT + "/cm?cmnd=Power" + SPECIFIC_ID + "%20%TOGGLE";
        toggleUrlParameter.setOutputId(SPECIFIC_ID);
        toggleUrlParameter.setPort(SPECIFIC_PORT);
        Assertions.assertEquals(expected, tasmotaUrlMapper.toRealUrlToggle(toggleUrlParameter));
    }
}
