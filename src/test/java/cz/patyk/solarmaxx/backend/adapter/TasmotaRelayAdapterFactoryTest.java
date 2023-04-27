package cz.patyk.solarmaxx.backend.adapter;

import cz.patyk.solarmaxx.DtoDataConstants;
import cz.patyk.solarmaxx.backend.client.TasmotaClient;
import cz.patyk.solarmaxx.backend.dto.data.RelayOutputDataDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.dto.relay.output.TasmotaOutputDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class TasmotaRelayAdapterFactoryTest {
    @Mock
    TasmotaClient tasmotaClient;
    TasmotaRelayAdapter tasmotaRelayAdapter;

    @BeforeEach
    void setUp() {
        tasmotaRelayAdapter = new TasmotaRelayAdapter(tasmotaClient);
    }

    @ParameterizedTest
    @MethodSource("provideStatuses")
    void updateStatusFromRelay(OutputStatus status, TasmotaOutputDto tasmotaOutputDto) {
        Mockito
                .when(tasmotaClient.getOutputStatusWithSpecificPortObject(any(URI.class), any(Byte.class)))
                .thenReturn(tasmotaOutputDto);

        RelayOutputDataDto relayOutputDataDto = tasmotaRelayAdapter.updateStatusFromRelay(DtoDataConstants.RELAY_OUTPUT_DATA_DTO_01);

        assertThat(relayOutputDataDto)
                .returns(status, RelayOutputDataDto::getDeviceOutputStatus);
    }

    private static Stream<Arguments> provideStatuses() {
        return Stream.of(
                Arguments.of(OutputStatus.ON, RelayAdapterConstants.TASMOTA_POWER_ON),
                Arguments.of(OutputStatus.OFF, RelayAdapterConstants.TASMOTA_POWER_OFF)
        );
    }

    @Test
    void turnOnRelayOutput() {
        Mockito
                .when(tasmotaClient.setOutputState(any(URI.class), any(Byte.class), any(String.class)))
                .thenReturn(RelayAdapterConstants.TASMOTA_POWER_ON);

        RelayOutputDataDto updatedDataDto = tasmotaRelayAdapter.turnOnRelayOutput(
                DtoDataConstants.RELAY_OUTPUT_DATA_DTO_01
        );

        assertThat(updatedDataDto)
                .returns(OutputStatus.ON, RelayOutputDataDto::getDeviceOutputStatus);
    }

    @Test
    void turnOffRelayOutput() {
        Mockito
                .when(tasmotaClient.setOutputState(any(URI.class), any(Byte.class), any(String.class)))
                .thenReturn(RelayAdapterConstants.TASMOTA_POWER_OFF);

        RelayOutputDataDto updatedDto = tasmotaRelayAdapter.turnOffRelayOutput(
                DtoDataConstants.RELAY_OUTPUT_DATA_DTO_01
        );

        assertThat(updatedDto)
                .returns(OutputStatus.OFF, RelayOutputDataDto::getDeviceOutputStatus);
    }
}