package cz.patyk.solarmaxx.backend.adapter;

import cz.patyk.solarmaxx.backend.client.TasmotaClient;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.dto.relay.output.RelayOutputDto;
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
class TasmotaRelayAdapterFactoryTest {
    @Mock
    TasmotaClient tasmotaClient;
    RelayOutputDto relayOutputDto;
    TasmotaRelayAdapter tasmotaRelayAdapter;

    @BeforeEach
    void setUp() {
        tasmotaRelayAdapter = new TasmotaRelayAdapter(tasmotaClient);
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
    void updateStatusFromRelay(OutputStatus status, String jsonMock) {
        Mockito
                .when(tasmotaClient.getOutputStatusWithSpecificPortObject(any(URI.class), any(Byte.class)))
                .thenReturn(jsonMock);

        RelayOutputDto updatedDto = tasmotaRelayAdapter.updateStatusFromRelay(relayOutputDto);

        assertThat(updatedDto)
                .returns(status, RelayOutputDto::getOutputStatus);
    }

    private static Stream<Arguments> provideStatuses() {
        return Stream.of(
                Arguments.of(OutputStatus.ON, "{\"POWER\": \"ON\"}"),
                Arguments.of(OutputStatus.ON, "{\"POWER1\": \"ON\"}"),
                Arguments.of(OutputStatus.OFF, "{\"POWER\": \"OFF\"}"),
                Arguments.of(OutputStatus.OFF, "{\"POWER1\": \"OFF\"}")
        );
    }

    @Test
    void turnOnRelayOutput() {
        Mockito
                .when(tasmotaClient.setOutputState(any(URI.class), any(Byte.class), any(String.class)))
                .thenReturn("{\"POWER\": \"ON\"}");

        RelayOutputDto updatedDto = tasmotaRelayAdapter.turnOnRelayOutput(relayOutputDto);

        assertThat(updatedDto)
                .returns(OutputStatus.ON, RelayOutputDto::getOutputStatus);
    }

    @Test
    void turnOffRelayOutput() {
        Mockito
                .when(tasmotaClient.setOutputState(any(URI.class), any(Byte.class), any(String.class)))
                .thenReturn("{\"POWER\": \"OFF\"}");

        RelayOutputDto updatedDto = tasmotaRelayAdapter.turnOffRelayOutput(relayOutputDto);

        assertThat(updatedDto)
                .returns(OutputStatus.OFF, RelayOutputDto::getOutputStatus);
    }
}