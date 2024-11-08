package cz.patyk.solarmaxx.backend.dto.relay.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
public enum OutputStatus {
    ON("ON"),
    OFF("OFF"),
    NA("N/A");

    private final String state;

    public static OutputStatus fromString(String state) {
        return switch (StringUtils.upperCase(state)) {
            case "ON" -> OutputStatus.ON;
            case "OFF" -> OutputStatus.OFF;
            default -> OutputStatus.NA;
        };
    }

}
