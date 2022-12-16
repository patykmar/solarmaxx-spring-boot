package cz.patyk.solarmaxx.backend.dto.device.output;

import lombok.Data;

@Data
public class DeviceOutputDto {
    private Long outputId;
    private String outputStatus;
    private String statusUrl;
    private String turnOnUrl;
    private String turnOffUrl;
}
