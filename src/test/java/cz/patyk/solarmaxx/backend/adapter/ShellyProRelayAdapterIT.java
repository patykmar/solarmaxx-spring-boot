package cz.patyk.solarmaxx.backend.adapter;

import cz.patyk.solarmaxx.AbstractIntegrationTestWithWireMock;
import cz.patyk.solarmaxx.backend.dto.data.RelayOutputDataDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

class ShellyProRelayAdapterIT extends AbstractIntegrationTestWithWireMock {
    private static final String FILE_PREFIX = "src/test/resources/relay/%s/shelly/";

    @Autowired
    ShellyProRelayAdapter shellyProRelayAdapter;

    public ShellyProRelayAdapterIT() {
        super(FILE_PREFIX);
    }

    private static Stream<Arguments> updateStatusFromRelayTestProvider() {
        return Stream.of(
                Arguments.of(0, OutputStatus.ON, "shelly_status_01.json"),
                Arguments.of(0, OutputStatus.OFF, "shelly_status_02.json"),
                Arguments.of(0, OutputStatus.OFF, "shelly_status_03.json")
        );
    }

    @ParameterizedTest
    @MethodSource("updateStatusFromRelayTestProvider")
    void updateStatusFromRelayTest(int outputId, OutputStatus expectedOutputStatus, String mockFileName) {
        String shellyProUrl = String.format("/rpc/Switch.GetStatus?id=%d", outputId);
        setStubGetHttp200UrlEqualTo(shellyProUrl, mockFileName);


        RelayOutputDataDto requestRelayDataDto = dataDto.makeShellyProDataDto(outputId);

        RelayOutputDataDto relayOutputDataDto = shellyProRelayAdapter.updateStatusFromRelay(requestRelayDataDto);
        Assertions.assertThat(relayOutputDataDto)
                .isNotNull()
                .returns(requestRelayDataDto.getId(), RelayOutputDataDto::getId)
                .returns(requestRelayDataDto.getDescription(), RelayOutputDataDto::getDescription)
                .returns(requestRelayDataDto.getOutputId(), RelayOutputDataDto::getOutputId)
                .returns(requestRelayDataDto.getOutputStatus(), RelayOutputDataDto::getOutputStatus)
                .returns(expectedOutputStatus, RelayOutputDataDto::getDeviceOutputStatus)
                .returns(requestRelayDataDto.getRelayId(), RelayOutputDataDto::getRelayId)
                .returns(requestRelayDataDto.getRelayName(), RelayOutputDataDto::getRelayName)
                .returns(requestRelayDataDto.getRelayIpAddress(), RelayOutputDataDto::getRelayIpAddress)
                .returns(requestRelayDataDto.getRelayPort(), RelayOutputDataDto::getRelayPort)
                .returns(requestRelayDataDto.getRelayTypeName(), RelayOutputDataDto::getRelayTypeName)
                .returns(requestRelayDataDto.getRelayTypeString(), RelayOutputDataDto::getRelayTypeString)
                .returns(requestRelayDataDto.getRelayTypeEnum(), RelayOutputDataDto::getRelayTypeEnum);
    }
}
