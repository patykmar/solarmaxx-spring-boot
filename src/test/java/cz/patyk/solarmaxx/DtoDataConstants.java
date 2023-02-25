package cz.patyk.solarmaxx;

import cz.patyk.solarmaxx.backend.adapter.RelayAdapterConstants;
import cz.patyk.solarmaxx.backend.dto.data.RelayOutputDataDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import org.apache.commons.lang3.math.NumberUtils;

public class DtoDataConstants {
    private DtoDataConstants() {
    }

    public static final RelayOutputDataDto RELAY_OUTPUT_DATA_DTO = RelayOutputDataDto.builder()
            .id(NumberUtils.LONG_ONE)
            .description(ValueConstants.RELAY_OUTPUT_DATA_DTO_DESCRIPTION)
            .outputId(NumberUtils.BYTE_ONE)
            .outputStatus(OutputStatus.OFF)
            .relayId(NumberUtils.LONG_ONE)
            .relayName("Relay name DTO")
            .relayIpAddress(RelayAdapterConstants.FAKE_IP)
            .relayPort((short) 80)
            .relayTypeId(NumberUtils.LONG_ONE)
            .relayTypeName("Relay type name")
            .build();
}
