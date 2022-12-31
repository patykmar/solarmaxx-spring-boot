package cz.patyk.solarmaxx.backend.dto.relay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@AllArgsConstructor
public enum SupportedRelayType {
    TASMOTA("tasmota"),
    SHELLY_PRO("shelly-pro");

    @Getter
    private final String relayType;

    public static SupportedRelayType fromString(String relayType) {
        return switch (StringUtils.lowerCase(relayType)) {
            case "shelly-pro" -> SupportedRelayType.SHELLY_PRO;
            case "tasmota" -> SupportedRelayType.TASMOTA;
            default -> throw new IllegalArgumentException(relayType + " is not supported device type");
        };
    }
}
