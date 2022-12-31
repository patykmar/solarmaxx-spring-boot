package cz.patyk.solarmaxx.backend.dto.relay.type;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RelayTypeConstants {
    public static final Byte TASMOTA_DEFAULT_OUTPUT_ID = 1;
    public static final Byte SHALLY_PRO_DEFAULT_OUTPUT_ID = 0;

    public static final String RELAY_TYPE_URL_PATTERN_IP = "%IP";
    public static final String RELAY_TYPE_URL_PATTERN_ID = "%ID";
    public static final String RELAY_TYPE_URL_PATTERN_PORT = "%PORT";
    public static final String RELAY_TYPE_URL_PATTERN_TOGGLE = "%TOGGLE";
}
