package cz.patyk.solarmaxx.backend.dto.out;

import lombok.Data;

@Data
public class DeviceTypeDtoOut {
    private Long id;
    private String name;
    private String urlStatusTemplate;
    private String urlToggleTemplate;
    private String deviceTypeString;
}
