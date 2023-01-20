package cz.patyk.solarmaxx.backend.mapper.relay.type.output;

import cz.patyk.solarmaxx.EntityConstants;
import cz.patyk.solarmaxx.backend.adapter.ShellyProRelayAdapter;
import cz.patyk.solarmaxx.backend.client.ShellyProClient;
import cz.patyk.solarmaxx.backend.config.RelayTypeConfig;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.dto.relay.output.RelayOutputDto;
import cz.patyk.solarmaxx.backend.dto.relay.type.RelayTypeConstants;
import cz.patyk.solarmaxx.backend.mapper.relay.OutputStatusMapper;
import cz.patyk.solarmaxx.backend.mapper.relay.type.url.ShellyProUrlMapper;
import cz.patyk.solarmaxx.backend.mapper.relay.type.url.UrlParameterMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.util.List;

class ShellyProOutputIdMapperTest {
    private final UrlParameterMapper urlParameterMapper = Mappers.getMapper(UrlParameterMapper.class);
    private ShellyProOutputIdMapper shellyProOutputIdMapper;

    @BeforeEach
    void setUp() {
        RelayTypeConfig relayTypeConfig = new RelayTypeConfig();
        ShellyProUrlMapper shellyProUrlMapper = new ShellyProUrlMapper(relayTypeConfig.relayTypeUrlPattern());
        ShellyProClient shellyProClient = Mockito.mock(ShellyProClient.class);
        ShellyProRelayAdapter shellyProRelayAdapter = new ShellyProRelayAdapter(new OutputStatusMapper(), shellyProClient);
        shellyProOutputIdMapper = new ShellyProOutputIdMapper(urlParameterMapper, shellyProUrlMapper, shellyProRelayAdapter);
    }

    @Test
    void getDeviceOutputsStatusNa() {
        List<RelayOutputDto> deviceOutputs = shellyProOutputIdMapper.getDeviceOutputs(EntityConstants.RELAY_SHELLY_PRO_ADMIN);

        String expectedStatusUrl = "http://1.2.3.4:80/rpc/Switch.GetStatus?id=0";
        String expectedToggleUrl = "http://1.2.3.4:80/rpc/Switch.Toggle?id=0";

        Assertions.assertThat(deviceOutputs)
                .isNotEmpty()
                .hasSize(EntityConstants.RELAY_SHELLY_PRO_ADMIN.getOutputCount())

                .element(RelayTypeConstants.SHALLY_PRO_DEFAULT_OUTPUT_ID)
                .returns(RelayTypeConstants.SHALLY_PRO_DEFAULT_OUTPUT_ID, RelayOutputDto::getOutputId)
                .returns(OutputStatus.NA, RelayOutputDto::getOutputStatus)
                .returns(expectedStatusUrl, RelayOutputDto::getStatusUrl)
                .returns(expectedToggleUrl, RelayOutputDto::getTurnOnUrl)
                .returns(expectedToggleUrl, RelayOutputDto::getTurnOffUrl)
        ;
    }
}