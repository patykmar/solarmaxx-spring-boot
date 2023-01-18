package cz.patyk.solarmaxx.backend.adapter;

import cz.patyk.solarmaxx.backend.client.ShellyProClient;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.dto.relay.output.RelayOutputDto;
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
    static String WAS_FALSE = "{\"was_on\":false}";
    static String WAS_TRUE = "{\"was_on\":true}";
    static String STATUS_DOWN = "{\"id\":0, \"source\":\"http\", \"output\":false, \"apower\":0.0, \"voltage\":226.9, \"current\":0.000, \"pf\":0.00, \"aenergy\":{\"total\":0.000,\"by_minute\":[0.000,0.000,0.000],\"minute_ts\":1673860473},\"temperature\":{\"tC\":29.1, \"tF\":84.4}}";
    static String STATUS_UP = "{\"id\":0, \"source\":\"http\", \"output\":true, \"apower\":0.0, \"voltage\":226.9, \"current\":0.000, \"pf\":0.00, \"aenergy\":{\"total\":0.000,\"by_minute\":[0.000,0.000,0.000],\"minute_ts\":1673860473},\"temperature\":{\"tC\":29.1, \"tF\":84.4}}";

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
    void getOutputStatusWithSpecificPortObjectTest(OutputStatus status, String jsonMock) {
        Mockito
                .when(shellyProClient.getOutputStatusWithSpecificPortObject(any(URI.class), any(Byte.class)))
                .thenReturn(jsonMock);

        assertThat(shellyProRelayAdapter.updateStatusFromRelay(relayOutputDto, "1.2.3.4"))
                .returns(status, RelayOutputDto::getOutputStatus);
    }

    static Stream<Arguments> provideStatuses() {
        return Stream.of(
                Arguments.of(OutputStatus.ON, STATUS_UP),
                Arguments.of(OutputStatus.OFF, STATUS_DOWN)
        );
    }


    @ParameterizedTest
    @MethodSource("provideToggle")
    void updateStatusFromRelay(OutputStatus status, String jsonMock) {
        Mockito
                .when(shellyProClient.setOutputState(any(URI.class), any(Byte.class)))
                .thenReturn(jsonMock);

        assertThat(shellyProRelayAdapter.turnOnRelayOutput(relayOutputDto, RelayAdapterConstants.FAKE_IP))
                .returns(status, RelayOutputDto::getOutputStatus);
    }

    static Stream<Arguments> provideToggle() {
        return Stream.of(
                Arguments.of(OutputStatus.ON, WAS_FALSE),
                Arguments.of(OutputStatus.OFF, WAS_TRUE)
        );
    }
}
