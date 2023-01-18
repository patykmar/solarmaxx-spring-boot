package cz.patyk.solarmaxx.backend.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
                    URI.create(HTTP + ip),
                    relayOutputDto.getOutputId()
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
        String response = tasmotaClient.setOutputState(
                URI.create(HTTP + ip), relayOutputDto.getOutputId(), toggle
        );

        return parseResponseAndUpdateState(relayOutputDto, response);
    }

    private RelayOutputDto parseResponseAndUpdateState(RelayOutputDto relayOutputDto, String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        TasmotaOutputDto tasmotaOutputDto;
        try {
            tasmotaOutputDto = objectMapper.readValue(response, TasmotaOutputDto.class);
        } catch (JsonProcessingException e) {
            tasmotaOutputDto = TasmotaOutputDto.builder().state("N/A").build();
            log.error("Cannot parse state from Tasmota output URL: {}", relayOutputDto.getStatusUrl());
        }

        relayOutputDto.setOutputStatus(
                OutputStatus.fromString(tasmotaOutputDto.getState())
        );
        return relayOutputDto;
    }

    private RelayOutputDto parseResponseAndUpdateState(RelayOutputDto relayOutputDto, TasmotaOutputDto tasmotaOutputDto) {
        relayOutputDto.setOutputStatus(OutputStatus.fromString(tasmotaOutputDto.getState()));
        return relayOutputDto;
    }

}
