package cz.patyk.solarmaxx.backend.dto.relay.output;

import lombok.Data;

@Data
public class RelayOutputDto {
    private Long outputId;
    private String outputStatus;
    private String statusUrl;
    private String turnOnUrl;
    private String turnOffUrl;
}
