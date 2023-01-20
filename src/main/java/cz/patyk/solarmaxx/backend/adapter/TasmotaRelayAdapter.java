package cz.patyk.solarmaxx.backend.adapter;

import cz.patyk.solarmaxx.backend.client.TasmotaClient;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.dto.relay.output.RelayOutputDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.TasmotaOutputDto;
import feign.RetryableException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class TasmotaRelayAdapter implements RelayAdapter {
    private static final String HTTP = "http://";
    public static final String TOGGLE_ON = "ON";
    public static final String TOGGLE_OFF = "OFF";

    private final TasmotaClient tasmotaClient;

    @Override
    public RelayOutputDto updateStatusFromRelay(@NonNull RelayOutputDto relayOutputDto, String ip) {
        TasmotaOutputDto outputStatusWithSpecificPortObject;
        try {
            outputStatusWithSpecificPortObject = tasmotaClient.getOutputStatusWithSpecificPortObject(
                    createBasicUrl(ip), relayOutputDto.getOutputId()
            );
        } catch (RetryableException e) {
            log.warn("Cannot connect to {}, due timeout.", ip);
            outputStatusWithSpecificPortObject = TasmotaOutputDto.builder().state("NA").build();
        }
        return parseResponseAndUpdateState(relayOutputDto, outputStatusWithSpecificPortObject);
    }

    @Override
    public RelayOutputDto turnOnRelayOutput(@NonNull RelayOutputDto relayOutputDto, String ip) {
        return toggleRelayOutput(relayOutputDto, ip, TOGGLE_ON);
    }

    @Override
    public RelayOutputDto turnOffRelayOutput(@NonNull RelayOutputDto relayOutputDto, String ip) {
        return toggleRelayOutput(relayOutputDto, ip, TOGGLE_OFF);
    }

    private RelayOutputDto toggleRelayOutput(RelayOutputDto relayOutputDto, String ip, String toggle) {
        TasmotaOutputDto outputStatusWithSpecificPortObject;
        try {
            outputStatusWithSpecificPortObject = tasmotaClient.setOutputState(
                    createBasicUrl(ip), relayOutputDto.getOutputId(), toggle
            );
        } catch (RetryableException e) {
            log.warn("Cannot connect to {}, due timeout.", ip);
            outputStatusWithSpecificPortObject = TasmotaOutputDto.builder().state("NA").build();
        }

        return parseResponseAndUpdateState(relayOutputDto, outputStatusWithSpecificPortObject);
    }

    private RelayOutputDto parseResponseAndUpdateState(RelayOutputDto relayOutputDto, TasmotaOutputDto tasmotaOutputDto) {
        relayOutputDto.setOutputStatus(OutputStatus.fromString(tasmotaOutputDto.getState()));
        return relayOutputDto;
    }

    private URI createBasicUrl(String ip) {
        return URI.create(HTTP + ip);
    }

}
