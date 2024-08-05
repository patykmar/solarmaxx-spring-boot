package cz.patyk.solarmaxx.backend.adapter;

import cz.patyk.solarmaxx.backend.client.TasmotaClient;
import cz.patyk.solarmaxx.backend.dto.data.RelayOutputDataDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.dto.relay.output.TasmotaOutputDto;
import feign.RetryableException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class TasmotaRelayAdapter implements RelayAdapter {
    public static final String LOG_MSG = "Cannot connect to {}, due timeout.";
    public static final String TOGGLE_ON = "ON";
    public static final String TOGGLE_OFF = "OFF";

    private final TasmotaClient tasmotaClient;

    @Override
    public RelayOutputDataDto updateStatusFromRelay(@NonNull RelayOutputDataDto relayOutputDataDto) {
        TasmotaOutputDto outputStatusWithSpecificPortObject;

        try {
            outputStatusWithSpecificPortObject = tasmotaClient.getOutputStatusWithSpecificPortObject(
                    AdapterUtils.createInsecureBasicUrl(relayOutputDataDto),
                    relayOutputDataDto.getOutputId()
            );
        } catch (RetryableException e) {
            log.warn(LOG_MSG, relayOutputDataDto.getRelayIpAddress());
            outputStatusWithSpecificPortObject = TasmotaOutputDto.builder().state("NA").build();
        }
        return parseResponseAndUpdateState(relayOutputDataDto, outputStatusWithSpecificPortObject);
    }

    @Override
    public RelayOutputDataDto turnOnRelayOutput(@NonNull RelayOutputDataDto relayOutputDataDto) {
        return toggleRelayOutput(relayOutputDataDto, TOGGLE_ON);
    }

    @Override
    public RelayOutputDataDto turnOffRelayOutput(@NonNull RelayOutputDataDto relayOutputDataDto) {
        return toggleRelayOutput(relayOutputDataDto, TOGGLE_OFF);
    }

    private RelayOutputDataDto toggleRelayOutput(RelayOutputDataDto relayOutputDataDto, String toggle) {
        TasmotaOutputDto outputStatusWithSpecificPortObject;
        try {
            outputStatusWithSpecificPortObject = tasmotaClient.setOutputState(
                    AdapterUtils.createInsecureBasicUrl(relayOutputDataDto), relayOutputDataDto.getOutputId(), toggle
            );
        } catch (RetryableException e) {
            log.warn(LOG_MSG, relayOutputDataDto.getRelayIpAddress());
            outputStatusWithSpecificPortObject = TasmotaOutputDto.builder().state("NA").build();
        }

        return parseResponseAndUpdateState(relayOutputDataDto, outputStatusWithSpecificPortObject);
    }

    private RelayOutputDataDto parseResponseAndUpdateState(RelayOutputDataDto relayOutputDto, TasmotaOutputDto tasmotaOutputDto) {
        relayOutputDto.setDeviceOutputStatus(OutputStatus.fromString(tasmotaOutputDto.getState()));
        return relayOutputDto;
    }
}
