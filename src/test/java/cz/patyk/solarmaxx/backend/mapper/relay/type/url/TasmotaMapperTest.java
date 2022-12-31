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
        statusUrlParameter.setUrlTemplate(TestRelayConstants.URL_STATUS_TASMOTA);
        toggleUrlParameter.setUrlTemplate(TestRelayConstants.URL_TOGGLE_TASMOTA);
        tasmotaUrlMapper = new TasmotaUrlMapper(relayTypeUrlPatternDto);
    }

    @Test
    void toRealUrlStatusNotSpecifyOutputId() {
        String expected = "http://" + TestRelayConstants.RELAY_IP + ":80/cm?cmnd=Power1%20STATUS";
        Assertions.assertEquals(expected, tasmotaUrlMapper.toRealUrlStatus(statusUrlParameter));
    }

    @Test
    void toRealUrlStatusSpecifyOutputId() {
        String expected = "http://" + TestRelayConstants.RELAY_IP + ":80/cm?cmnd=Power" + SPECIFIC_ID + "%20STATUS";
        statusUrlParameter.setOutputId(SPECIFIC_ID);
        Assertions.assertEquals(expected, tasmotaUrlMapper.toRealUrlStatus(statusUrlParameter));
    }

    @Test
    void toRealUrlStatusSpecifyOutputIdAndPort() {
        String expected = "http://" + TestRelayConstants.RELAY_IP + ":" + SPECIFIC_PORT + "/cm?cmnd=Power" + SPECIFIC_ID + "%20STATUS";
        statusUrlParameter.setOutputId(SPECIFIC_ID);
        statusUrlParameter.setPort(SPECIFIC_PORT);
        Assertions.assertEquals(expected, tasmotaUrlMapper.toRealUrlStatus(statusUrlParameter));
    }

    @Test
    void toRealUrlToggleNotSpecifyOutputId() {
        String expected = "http://" + TestRelayConstants.RELAY_IP + ":80/cm?cmnd=Power1%20%TOGGLE";
        Assertions.assertEquals(expected, tasmotaUrlMapper.toRealUrlToggle(toggleUrlParameter));
    }

    @Test
    void toRealUrlToggleSpecifyOutputId() {
        String expected = "http://" + TestRelayConstants.RELAY_IP + ":80/cm?cmnd=Power" + SPECIFIC_ID + "%20%TOGGLE";
        toggleUrlParameter.setOutputId(SPECIFIC_ID);
        Assertions.assertEquals(expected, tasmotaUrlMapper.toRealUrlToggle(toggleUrlParameter));
    }

    @Test
    void toRealUrlToggleSpecifyOutputIdAndPort() {
        String expected = "http://" + TestRelayConstants.RELAY_IP + ":" + SPECIFIC_PORT + "/cm?cmnd=Power" + SPECIFIC_ID + "%20%TOGGLE";
        toggleUrlParameter.setOutputId(SPECIFIC_ID);
        toggleUrlParameter.setPort(SPECIFIC_PORT);
        Assertions.assertEquals(expected, tasmotaUrlMapper.toRealUrlToggle(toggleUrlParameter));
    }

    @Test
    void toRealUrlToggleSpecifyOutputIdPortAndToggleOn() {
        String expected = "http://" + TestRelayConstants.RELAY_IP + ":" + SPECIFIC_PORT + "/cm?cmnd=Power" + SPECIFIC_ID + "%20ON";
        toggleUrlParameter.setOutputId(SPECIFIC_ID);
        toggleUrlParameter.setPort(SPECIFIC_PORT);
        toggleUrlParameter.setToggle("ON");
        Assertions.assertEquals(expected, tasmotaUrlMapper.toRealUrlToggle(toggleUrlParameter));
    }

    @Test
    void toRealUrlToggleSpecifyOutputIdPortAndToggleOff() {
        String expected = "http://" + TestRelayConstants.RELAY_IP + ":" + SPECIFIC_PORT + "/cm?cmnd=Power" + SPECIFIC_ID + "%20OFF";
        toggleUrlParameter.setOutputId(SPECIFIC_ID);
        toggleUrlParameter.setPort(SPECIFIC_PORT);
        toggleUrlParameter.setToggle("OFF");
        Assertions.assertEquals(expected, tasmotaUrlMapper.toRealUrlToggle(toggleUrlParameter));
    }
}
