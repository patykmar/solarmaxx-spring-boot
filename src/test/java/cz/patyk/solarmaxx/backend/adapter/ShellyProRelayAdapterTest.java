package cz.patyk.solarmaxx.backend.adapter;

import cz.patyk.solarmaxx.backend.client.ShellyProClient;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.dto.relay.output.RelayOutputDto;
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
    RelayOutputDto relayOutputDto;
    ShellyProRelayAdapter shellyProRelayAdapter;

    @BeforeEach
    void setUp() {
        shellyProRelayAdapter = new ShellyProRelayAdapter(new OutputStatusMapper(), shellyProClient);
        relayOutputDto = RelayOutputDto.builder()
                .outputId((byte) 1)
                .statusUrl("statusUrl")
                .turnOnUrl("turnOnUrl")
                .turnOffUrl("turnOffUrl")
                .outputStatus(OutputStatus.NA)
                .build();
    }

    @ParameterizedTest
    @MethodSource("provideStatuses")
    void getOutputStatusWithSpecificPortObjectTest(OutputStatus status, ShellyProStatusOutputDto shellyProStatusOutputDto) {
        Mockito
                .when(shellyProClient.getOutputStatusWithSpecificPortObject(any(URI.class), any(Byte.class)))
                .thenReturn(shellyProStatusOutputDto);

        assertThat(shellyProRelayAdapter.updateStatusFromRelay(relayOutputDto, "1.2.3.4"))
                .returns(status, RelayOutputDto::getOutputStatus);
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

        assertThat(shellyProRelayAdapter.turnOnRelayOutput(relayOutputDto, RelayAdapterConstants.FAKE_IP))
                .returns(status, RelayOutputDto::getOutputStatus);
    }

    static Stream<Arguments> provideToggle() {
        return Stream.of(
                Arguments.of(OutputStatus.ON, RelayAdapterConstants.SHELLY_PRO_WAS_OFF),
                Arguments.of(OutputStatus.OFF, RelayAdapterConstants.SHELLY_PRO_WAS_ON)
        );
    }
}
