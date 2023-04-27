package cz.patyk.solarmaxx.backend;

import cz.patyk.solarmaxx.backend.adapter.RelayAdapterConstants;
import cz.patyk.solarmaxx.backend.dto.relay.output.TasmotaOutputDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.shellypro.ShellyProStatusOutputDto;

public class OutputDtoConstants {

    public static final ShellyProStatusOutputDto SHELLY_PRO_STATUS_FALSE = ShellyProStatusOutputDto.builder()
            .state(false)
            .build();
    public static final ShellyProStatusOutputDto SHELLY_PRO_STATUS_TRUE = ShellyProStatusOutputDto.builder()
            .state(true)
            .build();
    public static final TasmotaOutputDto TASMOTA_OUTPUT_DTO_ON = TasmotaOutputDto.builder()
            .state(RelayAdapterConstants.TASMOTA_ON)
            .build();
    public static final TasmotaOutputDto TASMOTA_OUTPUT_DTO_OFF = TasmotaOutputDto.builder()
            .state(RelayAdapterConstants.TASMOTA_OFF)
            .build();
    public static final TasmotaOutputDto TASMOTA_OUTPUT_DTO_NA = TasmotaOutputDto.builder()
            .state(RelayAdapterConstants.TASMOTA_NA)
            .build();
}
