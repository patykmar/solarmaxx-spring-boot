package cz.patyk.solarmaxx.backend.dto.relay.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelayOutputDto {
    private Byte outputId;
    private OutputStatus outputStatus;
    private String statusUrl;
    private String turnOnUrl;
    private String turnOffUrl;
}
