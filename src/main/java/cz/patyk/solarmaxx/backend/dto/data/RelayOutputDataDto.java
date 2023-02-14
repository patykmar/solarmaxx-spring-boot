package cz.patyk.solarmaxx.backend.dto.data;

import cz.patyk.solarmaxx.backend.dto.RelayDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelayOutputDataDto implements DtoDataInterface {
    private Long id;
    private String description;
    private Byte outputId;
    private OutputStatus outputStatus;
    private RelayDto relayDto;
}
