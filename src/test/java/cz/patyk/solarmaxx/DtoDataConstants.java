package cz.patyk.solarmaxx;

import cz.patyk.solarmaxx.backend.adapter.RelayAdapterConstants;
import cz.patyk.solarmaxx.backend.dto.data.RelayOutputDataDto;
import cz.patyk.solarmaxx.backend.dto.relay.RelayConstants;
import cz.patyk.solarmaxx.backend.dto.relay.SupportedRelayType;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

public class DtoDataConstants {
    public static final RelayOutputDataDto RELAY_OUTPUT_DATA_DTO_01 =
            getTasmotaRelayOutputDataDtoDefaulPort(NumberUtils.LONG_ONE);
    public static final RelayOutputDataDto RELAY_OUTPUT_DATA_DTO_02 =
            getTasmotaRelayOutputDataDtoDefaulPort(2L);
    public static final RelayOutputDataDto RELAY_OUTPUT_DATA_DTO_03 =
            getShellyProRelayOutputDataDtoDefaultPort(3L);
    public static final List<RelayOutputDataDto> RELAY_OUTPUT_DATA_DTOS =
            List.of(RELAY_OUTPUT_DATA_DTO_01, RELAY_OUTPUT_DATA_DTO_02, RELAY_OUTPUT_DATA_DTO_03);

    private DtoDataConstants() {
    }

    public static RelayOutputDataDto getRelayOutputDataDto(
            Long id, String typeName, SupportedRelayType supportedRelayType, int port
    ) {
        return RelayOutputDataDto.builder()
                .id(id)
                .description(ValueConstants.RELAY_OUTPUT_DATA_DTO_DESCRIPTION)
                .outputId(NumberUtils.BYTE_ONE)
                .outputStatus(OutputStatus.OFF)
                .relayId(NumberUtils.LONG_ONE)
                .relayName("Relay name DTO " + id)
                .relayIpAddress(RelayAdapterConstants.FAKE_IP)
                .relayPort(port)
                .relayTypeId(NumberUtils.LONG_ONE)
                .relayTypeName("Relay type name " + typeName)
                .relayTypeString(typeName)
                .relayTypeEnum(supportedRelayType)
                .build();
    }

    public static RelayOutputDataDto getTasmotaRelayOutputDataDto(Long id, int port) {
        return getRelayOutputDataDto(
                id, RelayConstants.DEVICE_TYPE_TASMOTA, SupportedRelayType.TASMOTA, port
        );
    }

    public static RelayOutputDataDto getTasmotaRelayOutputDataDtoDefaulPort(Long id) {
        return getTasmotaRelayOutputDataDto(id, 80);
    }

    public static RelayOutputDataDto getShellyProRelayOutputDataDto(Long id, int port) {
        return getRelayOutputDataDto(
                id, RelayConstants.DEVICE_TYPE_SHELLY_PRO, SupportedRelayType.SHELLY_PRO, Short.parseShort(String.valueOf(port))
        );
    }

    public static RelayOutputDataDto getShellyProRelayOutputDataDtoDefaultPort(Long id) {
        return getShellyProRelayOutputDataDto(id,80);
    }
}
