package cz.patyk.solarmaxx.backend.adapter;

import cz.patyk.solarmaxx.DtoDataConstants;
import cz.patyk.solarmaxx.backend.client.ShellyProClient;
import cz.patyk.solarmaxx.backend.dto.data.RelayOutputDataDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.dto.relay.output.shellypro.ShellyProStatusOutputDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.shellypro.ShellyProToggleOutputDto;
import cz.patyk.solarmaxx.backend.mapper.relay.OutputStatusMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ShellyProRelayAdapterTest {
    @Mock
    ShellyProClient shellyProClient;
    ShellyProRelayAdapter shellyProRelayAdapter;

    @BeforeEach
    void setUp() {
        shellyProRelayAdapter = new ShellyProRelayAdapter(new OutputStatusMapper(), shellyProClient);
    }

    @ParameterizedTest
    @MethodSource("provideStatuses")
    void getOutputStatusWithSpecificPortObjectTest(OutputStatus status, ShellyProStatusOutputDto shellyProStatusOutputDto) {
        Mockito
                .when(shellyProClient.getOutputStatusWithSpecificPortObject(any(URI.class), any(Byte.class)))
                .thenReturn(shellyProStatusOutputDto);

        RelayOutputDataDto relayOutputDataDto = shellyProRelayAdapter.updateStatusFromRelay(DtoDataConstants.RELAY_OUTPUT_DATA_DTO_01);

        assertThat(relayOutputDataDto)
                .returns(status, RelayOutputDataDto::getDeviceOutputStatus);
    }

    static Stream<Arguments> provideStatuses() {
        return Stream.of(
                Arguments.of(OutputStatus.ON, RelayAdapterConstants.SHELLY_PRO_STATUS_ON),
                Arguments.of(OutputStatus.OFF, RelayAdapterConstants.SHELLY_PRO_STATUS_OFF)
        );
    }

    @ParameterizedTest
    @MethodSource("provideToggle")
    void updateStatusFromRelay(OutputStatus status, ShellyProToggleOutputDto shellyProToggleOutputDto) {
        Mockito
                .when(shellyProClient.setOutputState(any(URI.class), any(Byte.class)))
                .thenReturn(shellyProToggleOutputDto);

        assertThat(shellyProRelayAdapter.turnOnRelayOutput(DtoDataConstants.RELAY_OUTPUT_DATA_DTO_01))
                .returns(status, RelayOutputDataDto::getDeviceOutputStatus);
    }

    static Stream<Arguments> provideToggle() {
        return Stream.of(
                Arguments.of(OutputStatus.ON, RelayAdapterConstants.SHELLY_PRO_WAS_OFF),
                Arguments.of(OutputStatus.OFF, RelayAdapterConstants.SHELLY_PRO_WAS_ON)
        );
    }
}
