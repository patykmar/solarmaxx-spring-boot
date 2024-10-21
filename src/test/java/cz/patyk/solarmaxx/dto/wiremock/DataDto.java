package cz.patyk.solarmaxx.dto.wiremock;

import cz.patyk.solarmaxx.ValueConstants;
import cz.patyk.solarmaxx.backend.adapter.RelayAdapterConstants;
import cz.patyk.solarmaxx.backend.dto.data.RelayOutputDataDto;
import cz.patyk.solarmaxx.backend.dto.relay.SupportedRelayType;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import org.apache.commons.lang3.math.NumberUtils;

public class DataDto extends AbstractWiremockDto {

    public DataDto(int port) {
        super(port);
    }

    public RelayOutputDataDto makeShellyProDataDto(int outputId) {
        return makeRelayOutputDataDto(NumberUtils.LONG_ONE, SupportedRelayType.SHELLY_PRO, outputId);
    }

    public RelayOutputDataDto makeTasmotaDataDto(int outputId) {
        return makeRelayOutputDataDto(NumberUtils.LONG_ONE, SupportedRelayType.TASMOTA, outputId);
    }

    private RelayOutputDataDto makeRelayOutputDataDto(Long id, SupportedRelayType relayType, int outputId) {
        return RelayOutputDataDto.builder()
                .id(id)
                .description(ValueConstants.RELAY_OUTPUT_DATA_DTO_DESCRIPTION)
                .outputId(outputId)
                .outputStatus(OutputStatus.OFF)
                .relayId(NumberUtils.LONG_ONE)
                .relayName("Relay name DTO " + id)
                .relayIpAddress(RelayAdapterConstants.LOCALHOST)
                .relayPort(wiremockPort)
                .relayTypeId(NumberUtils.LONG_ONE)
                .relayTypeName("Relay type name " + relayType.getRelayType())
                .relayTypeString(relayType.getRelayType())
                .relayTypeEnum(relayType)
                .build();
    }
}
