package cz.patyk.solarmaxx.backend.dto.relay.output;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ShellyRelayOutputDto extends RelayOutputDto {
    private Float voltage;
    private Float temperatureC;
    private Float temperatureF;
}
