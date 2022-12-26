package cz.patyk.solarmaxx.backend.dto.relay;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RelayConstants {
    public static final String PORT_STATUS_ON = "ON";
    public static final String PORT_STATUS_OFF = "OFF";
    public static final String PORT_STATUS_NA = "N/A";
    public static final String DEVICE_TYPE_TASMOTA = "tasmota";
    public static final String DEVICE_TYPE_SHELLY_PRO = "shelly-pro";
}
