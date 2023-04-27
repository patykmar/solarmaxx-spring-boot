package cz.patyk.solarmaxx;

import cz.patyk.solarmaxx.backend.adapter.RelayAdapterConstants;
import cz.patyk.solarmaxx.backend.dto.data.RelayOutputDataDto;
import cz.patyk.solarmaxx.backend.dto.relay.RelayConstants;
import cz.patyk.solarmaxx.backend.dto.relay.SupportedRelayType;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

public class DtoDataConstants {
    private DtoDataConstants() {
    }

    public static RelayOutputDataDto getRelayOutputDataDto(Long id, String typeName, SupportedRelayType supportedRelayType) {
        return RelayOutputDataDto.builder()
                .id(id)
                .description(ValueConstants.RELAY_OUTPUT_DATA_DTO_DESCRIPTION)
                .outputId(NumberUtils.BYTE_ONE)
                .outputStatus(OutputStatus.OFF)
                .relayId(NumberUtils.LONG_ONE)
                .relayName("Relay name DTO " + id)
                .relayIpAddress(RelayAdapterConstants.FAKE_IP)
                .relayPort((short) 80)
                .relayTypeId(NumberUtils.LONG_ONE)
                .relayTypeName("Relay type name " + typeName)
                .relayTypeString(typeName)
                .relayTypeEnum(supportedRelayType)
                .build();
    }

    public static RelayOutputDataDto getTasmotaRelayOutputDataDto(Long id) {
        return getRelayOutputDataDto(id, RelayConstants.DEVICE_TYPE_TASMOTA, SupportedRelayType.TASMOTA);
    }

    public static RelayOutputDataDto getShellyProRelayOutputDataDto(Long id) {
        return getRelayOutputDataDto(id, RelayConstants.DEVICE_TYPE_SHELLY_PRO, SupportedRelayType.SHELLY_PRO);
    }

    public static final RelayOutputDataDto RELAY_OUTPUT_DATA_DTO_01 =
            getTasmotaRelayOutputDataDto(NumberUtils.LONG_ONE);

    public static final RelayOutputDataDto RELAY_OUTPUT_DATA_DTO_02 =
            getTasmotaRelayOutputDataDto(2L);

    public static final RelayOutputDataDto RELAY_OUTPUT_DATA_DTO_03 =
            getShellyProRelayOutputDataDto(3L);

    public static final List<RelayOutputDataDto> RELAY_OUTPUT_DATA_DTOS =
            List.of(RELAY_OUTPUT_DATA_DTO_01, RELAY_OUTPUT_DATA_DTO_02, RELAY_OUTPUT_DATA_DTO_03);
}
