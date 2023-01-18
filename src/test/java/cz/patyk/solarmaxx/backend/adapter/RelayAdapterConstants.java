package cz.patyk.solarmaxx.backend.adapter;

import cz.patyk.solarmaxx.backend.dto.relay.output.TasmotaOutputDto;

public class RelayAdapterConstants {
    private RelayAdapterConstants() {
    }

    public static final String FAKE_IP = "1.2.3.4";
    public static final String TASMOTA_ON = "ON";
    public static final String TASMOTA_OFF = "OFF";

    public static final String TASMOTA_STRING_POWER_ON = "{\"POWER\": \"ON\"}";
    public static final String TASMOTA_STRING_POWER1_ON = "{\"POWER1\": \"ON\"}";
    public static final String TASMOTA_STRING_POWER_OFF = "{\"POWER\": \"OFF\"}";
    public static final String TASMOTA_STRING_POWER1_OFF = "{\"POWER1\": \"OFF\"}";

    public static final TasmotaOutputDto TASMOTA_POWER_ON = TasmotaOutputDto.builder().state(TASMOTA_ON).build();
    public static final TasmotaOutputDto TASMOTA_POWER_OFF = TasmotaOutputDto.builder().state(TASMOTA_OFF).build();
}
