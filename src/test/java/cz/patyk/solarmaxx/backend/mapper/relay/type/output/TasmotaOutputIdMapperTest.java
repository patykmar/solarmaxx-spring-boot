package cz.patyk.solarmaxx.backend.mapper.relay.type.output;

import cz.patyk.solarmaxx.EntityConstants;
import cz.patyk.solarmaxx.backend.config.RelayTypeConfig;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.dto.relay.output.RelayOutputDto;
import cz.patyk.solarmaxx.backend.dto.relay.type.RelayTypeConstants;
import cz.patyk.solarmaxx.backend.mapper.relay.TestRelayConstants;
import cz.patyk.solarmaxx.backend.mapper.relay.type.url.TasmotaUrlMapper;
import cz.patyk.solarmaxx.backend.mapper.relay.type.url.UrlParameterMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TasmotaOutputIdMapperTest {
    private final UrlParameterMapper urlParameterMapper = Mappers.getMapper(UrlParameterMapper.class);
    private TasmotaOutputIdMapper tasmotaOutputIdMapper;

    @BeforeEach
    void setUp() {
        RelayTypeConfig relayTypeConfig = new RelayTypeConfig();
        TasmotaUrlMapper tasmotaUrlMapper = new TasmotaUrlMapper(relayTypeConfig.relayTypeUrlPattern());
        tasmotaOutputIdMapper = new TasmotaOutputIdMapper(urlParameterMapper, tasmotaUrlMapper);
    }

    @Test
    void getDeviceOutputsStatusNa() {
        List<RelayOutputDto> deviceOutputs = tasmotaOutputIdMapper.getDeviceOutputs(EntityConstants.RELAY_TASMOTA_ADMIN);

        String expectedStatusUrl = "http://" + TestRelayConstants.RELAY_IP + ":80/cm?cmnd=Power1%20STATUS";
        String expectedToggleUrlOn = "http://" + TestRelayConstants.RELAY_IP + ":80/cm?cmnd=Power" + RelayTypeConstants.TASMOTA_DEFAULT_OUTPUT_ID + "%20ON";
        String expectedToggleUrlOff = "http://" + TestRelayConstants.RELAY_IP + ":80/cm?cmnd=Power" + RelayTypeConstants.TASMOTA_DEFAULT_OUTPUT_ID + "%20OFF";

        Assertions.assertThat(deviceOutputs)
                .isNotEmpty()
                .hasSize(EntityConstants.RELAY_TASMOTA_ADMIN.getOutputCount())

                // tasmota start indexing port from 1
                .element(RelayTypeConstants.TASMOTA_DEFAULT_OUTPUT_ID - 1)
                .returns(RelayTypeConstants.TASMOTA_DEFAULT_OUTPUT_ID, RelayOutputDto::getOutputId)
                .returns(OutputStatus.NA, RelayOutputDto::getOutputStatus)
                .returns(expectedStatusUrl, RelayOutputDto::getStatusUrl)
                .returns(expectedToggleUrlOn, RelayOutputDto::getTurnOnUrl)
                .returns(expectedToggleUrlOff, RelayOutputDto::getTurnOffUrl);
    }
}