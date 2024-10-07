package cz.patyk.solarmaxx.backend.adapter;

import cz.patyk.solarmaxx.AbstractIntegrationTestWithWireMock;
import cz.patyk.solarmaxx.DtoDataConstants;
import cz.patyk.solarmaxx.backend.dto.data.RelayOutputDataDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class TasmotaRelayAdapterFactoryIT extends AbstractIntegrationTestWithWireMock {
    private static final String FILE_PREFIX = "src/test/resources/relay/%s/tasmota/";
    @Autowired
    TasmotaRelayAdapter tasmotaRelayAdapter;

    public TasmotaRelayAdapterFactoryIT() {
        super(FILE_PREFIX);
    }

    private static Stream<Arguments> provideStatuses() {
        return Stream.of(
                Arguments.of(OutputStatus.ON, 1, "tasmota_on.json"),
                Arguments.of(OutputStatus.OFF, 2, "tasmota_off.json")
        );
    }

    private void setUpStubWireMockTasmota(int outputId, String file) {
        var url = "/cm?cmnd=Power%id%%20STATUS".replace("%id%", String.valueOf(outputId));
        setStubGetHttp200UrlEqualTo(url, file);
    }

    @ParameterizedTest
    @MethodSource("provideStatuses")
    void updateStatusFromRelay(OutputStatus status, int outputId, String file) {

        setUpStubWireMockTasmota(outputId, file);

        RelayOutputDataDto relayOutputDataDto =
                tasmotaRelayAdapter.updateStatusFromRelay(DtoDataConstants.getTasmotaRelayOutputDataLocalhostDto(port, outputId));

        assertThat(relayOutputDataDto)
                .returns(status, RelayOutputDataDto::getDeviceOutputStatus);
    }

}