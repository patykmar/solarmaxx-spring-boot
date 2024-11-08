package cz.patyk.solarmaxx.backend.dto.relay;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@RequiredArgsConstructor
public enum SupportedRelayType {
    TASMOTA("tasmota"),
    SHELLY_PRO("shelly-pro");

    private final String relayType;

    public static SupportedRelayType fromString(String relayType) {
        return switch (StringUtils.lowerCase(relayType)) {
            case "shelly-pro" -> SupportedRelayType.SHELLY_PRO;
            case "tasmota" -> SupportedRelayType.TASMOTA;
            default -> throw new IllegalArgumentException(relayType + " is not supported device type");
        };
    }
}
