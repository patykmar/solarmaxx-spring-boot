package cz.patyk.solarmaxx.backend.dto.device.output;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ShellyDeviceOutputDto extends DeviceOutputDto {
    private Float voltage;
    private Float temperatureC;
    private Float temperatureF;
}
