package cz.patyk.solarmaxx.backend.adapter;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import cz.patyk.solarmaxx.DtoDataConstants;
import cz.patyk.solarmaxx.backend.dto.data.RelayOutputDataDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.dto.relay.output.TasmotaOutputDto;
import cz.patyk.solarmaxx.backend.utils.FileOperationUtils;
import cz.patyk.solarmaxx.backend.utils.PrefixPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.Stream;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

@SpringBootTest
@ActiveProfiles("IT")
public class TasmotaRelayAdapterFactoryIT {
    private static final String FILE_PREFIX = "src/test/resources/relay/%s/";


    @RegisterExtension
    protected static WireMockExtension wm1 = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    protected final WireMockRuntimeInfo wm1RuntimeInfo = wm1.getRuntimeInfo();
    protected final int port = wm1RuntimeInfo.getHttpPort();
    //    protected final DbEntityMock dbEntityMock = new DbEntityMock(port);
    protected final FileOperationUtils fileOperationUtils = new FileOperationUtils(FILE_PREFIX);

    @Autowired
    TasmotaRelayAdapter tasmotaRelayAdapter;

    private static Stream<Arguments> provideStatuses() {
        return Stream.of(
                Arguments.of(OutputStatus.ON, RelayAdapterConstants.TASMOTA_POWER_ON),
                Arguments.of(OutputStatus.OFF, RelayAdapterConstants.TASMOTA_POWER_OFF)
        );
    }

    @BeforeEach
    void setUp() {
        wm1.stubFor(get(urlEqualTo("/cm?cmnd=Power2%20STATUS"))
                .willReturn(
                        ok(fileOperationUtils.loadFileContextAsString("tasmota_on.json", PrefixPath.JSON))
                ));
    }

    @ParameterizedTest
    @MethodSource("provideStatuses")
    void updateStatusFromRelay(OutputStatus status, TasmotaOutputDto tasmotaOutputDto) {
//        Mockito
//                .when(tasmotaClient.getOutputStatusWithSpecificPortObject(any(URI.class), any(Byte.class)))
//                .thenReturn(tasmotaOutputDto);

        RelayOutputDataDto relayOutputDataDto =
                tasmotaRelayAdapter.updateStatusFromRelay(DtoDataConstants.getTasmotaRelayOutputDataDto(1L, port));

        assertThat(relayOutputDataDto)
                .returns(status, RelayOutputDataDto::getDeviceOutputStatus);
    }

    @Test
    void turnOnRelayOutput() {
//        Mockito
//                .when(tasmotaClient.setOutputState(any(URI.class), any(Byte.class), any(String.class)))
//                .thenReturn(RelayAdapterConstants.TASMOTA_POWER_ON);

        RelayOutputDataDto updatedDataDto = tasmotaRelayAdapter.turnOnRelayOutput(
                DtoDataConstants.RELAY_OUTPUT_DATA_DTO_01
        );

        assertThat(updatedDataDto)
                .returns(OutputStatus.ON, RelayOutputDataDto::getDeviceOutputStatus);
    }

    @Test
    void turnOffRelayOutput() {
//        Mockito
//                .when(tasmotaClient.setOutputState(any(URI.class), any(Byte.class), any(String.class)))
//                .thenReturn(RelayAdapterConstants.TASMOTA_POWER_OFF);

        RelayOutputDataDto updatedDto = tasmotaRelayAdapter.turnOffRelayOutput(
                DtoDataConstants.RELAY_OUTPUT_DATA_DTO_01
        );

        assertThat(updatedDto)
                .returns(OutputStatus.OFF, RelayOutputDataDto::getDeviceOutputStatus);
    }
}