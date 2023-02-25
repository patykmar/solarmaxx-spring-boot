package cz.patyk.solarmaxx.backend.adapter;

import cz.patyk.solarmaxx.backend.dto.relay.output.TasmotaOutputDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.shellypro.ShellyProStatusOutputDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.shellypro.ShellyProToggleOutputDto;

public class RelayAdapterConstants {
    private RelayAdapterConstants() {
    }

    public static final String FAKE_IP = "1.2.3.4";
    public static final String TASMOTA_ON = "ON";
    public static final String TASMOTA_OFF = "OFF";
    public static final String TASMOTA_NA = "N/A";

    public static final String TASMOTA_STRING_POWER_ON = "{\"POWER\": \"ON\"}";
    public static final String TASMOTA_STRING_POWER1_ON = "{\"POWER1\": \"ON\"}";
    public static final String TASMOTA_STRING_POWER_OFF = "{\"POWER\": \"OFF\"}";
    public static final String TASMOTA_STRING_POWER1_OFF = "{\"POWER1\": \"OFF\"}";
    public static String WAS_FALSE = "{\"was_on\":false}";
    public static String WAS_TRUE = "{\"was_on\":true}";
    public static String STATUS_DOWN = "{\"id\":0, \"source\":\"http\", \"output\":false, \"apower\":0.0, \"voltage\":226.9, \"current\":0.000, \"pf\":0.00, \"aenergy\":{\"total\":0.000,\"by_minute\":[0.000,0.000,0.000],\"minute_ts\":1673860473},\"temperature\":{\"tC\":29.1, \"tF\":84.4}}";
    public static String STATUS_UP = "{\"id\":0, \"source\":\"http\", \"output\":true, \"apower\":0.0, \"voltage\":226.9, \"current\":0.000, \"pf\":0.00, \"aenergy\":{\"total\":0.000,\"by_minute\":[0.000,0.000,0.000],\"minute_ts\":1673860473},\"temperature\":{\"tC\":29.1, \"tF\":84.4}}";


    public static final TasmotaOutputDto TASMOTA_POWER_ON = TasmotaOutputDto.builder().state(TASMOTA_ON).build();
    public static final TasmotaOutputDto TASMOTA_POWER_OFF = TasmotaOutputDto.builder().state(TASMOTA_OFF).build();

    public static final ShellyProStatusOutputDto SHELLY_PRO_STATUS_ON = ShellyProStatusOutputDto.builder().state(true).build();
    public static final ShellyProStatusOutputDto SHELLY_PRO_STATUS_OFF = ShellyProStatusOutputDto.builder().state(false).build();
    public static final ShellyProToggleOutputDto SHELLY_PRO_WAS_ON = ShellyProToggleOutputDto.builder().state(true).build();
    public static final ShellyProToggleOutputDto SHELLY_PRO_WAS_OFF = ShellyProToggleOutputDto.builder().state(false).build();
}
