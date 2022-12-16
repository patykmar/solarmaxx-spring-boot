package cz.patyk.solarmaxx.backend.dto.in;

import lombok.Data;

@Data
public class DeviceTypeDtoIn {
    private Long id;
    private String name;
    private String urlStatus;
    private String urlToggle;
    private String turnOn;
    private String turnOff;
    private String deviceTypeString;
}
