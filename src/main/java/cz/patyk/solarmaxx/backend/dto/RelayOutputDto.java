package cz.patyk.solarmaxx.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelayOutputDto implements DtoInterface {
    private Long id;
    private String description;
    private Integer outputId;
    private String outputStatus;
    private Long relayId;
}
