package cz.patyk.solarmaxx.backend.mapper.relay;

public class TestRelayConstants {

    private TestRelayConstants() {
    }
    public static final Short RELAY_PORT = (short) 80;
    public static final String RELAY_IP = "1.2.3.4";
    public static final Byte RELAY_OUTPUT_COUNT = (byte) 8;
    public static final String URL_STATUS_TASMOTA = "http://%IP:%PORT/cm?cmnd=Power%ID%20STATUS";
    public static final String URL_STATUS_SHELLY_PRO = "http://%IP:%PORT/rpc/Switch.GetStatus?id=%ID";
    public static final String URL_TOGGLE_TASMOTA = "http://%IP:%PORT/cm?cmnd=Power%ID%20%TOGGLE";
    public static final String URL_TOGGLE_SHELLY_PRO = "http://%IP:%PORT/rpc/Switch.Toggle?id=%ID";
}
