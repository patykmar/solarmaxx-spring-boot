package cz.patyk.solarmaxx;

import cz.patyk.solarmaxx.backend.dto.data.RelayOutputDataDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import org.apache.commons.lang3.math.NumberUtils;

public class DtoDataConstants {
    private DtoDataConstants() {
    }

    private final RelayOutputDataDto relayOutputDataDto = RelayOutputDataDto.builder()
            .id(NumberUtils.LONG_ONE)
            .description(ValueConstants.RELAY_OUTPUT_DATA_DTO_DESCRIPTION)
            .outputId(NumberUtils.BYTE_ONE)
            .outputStatus(OutputStatus.OFF)
            .relayDto(DtoInConstants.RELAY_DTO)
            .build();

    public static final RelayOutputDataDto RELAY_OUTPUT_DATA_DTO = RelayOutputDataDto.builder()
            .id(NumberUtils.LONG_ONE)
            .description("Relay output data dto description")
            .outputId(NumberUtils.BYTE_ONE)
            .outputStatus(OutputStatus.ON)
            .relayDto(DtoInConstants.RELAY_DTO)
            .build();
}
