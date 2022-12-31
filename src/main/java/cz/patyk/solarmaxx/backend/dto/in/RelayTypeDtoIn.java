package cz.patyk.solarmaxx.backend.dto.in;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelayTypeDtoIn implements IDtoIn {
    private Long id;
    private String name;
    private String urlStatus;
    private String urlToggle;
    private String turnOn;
    private String turnOff;
    private String deviceTypeString;
}
