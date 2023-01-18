package cz.patyk.solarmaxx.backend.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.patyk.solarmaxx.backend.client.ShellyProClient;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.dto.relay.output.RelayOutputDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.shellypro.ShellyProStatusOutputDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.shellypro.ShellyProToggleOutputDto;
import cz.patyk.solarmaxx.backend.mapper.relay.OutputStatusMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShellyProRelayAdapter implements RelayAdapter {

    private static final String PARSE_ERROR_MESSAGE = "Cannot parse state from Shelly Pro output URL: {}";
    private final OutputStatusMapper outputStatusMapper;
    private final ShellyProClient shellyProClient;

    @Override
    public RelayOutputDto updateStatusFromRelay(@NonNull RelayOutputDto relayOutputDto, String ip) {
        String response = shellyProClient.getOutputStatusWithSpecificPortObject(
                URI.create(relayOutputDto.getStatusUrl()),
                relayOutputDto.getOutputId()
        );
        return parseStatusResponseAndUpdateState(relayOutputDto, response);
    }

    @Override
    public RelayOutputDto turnOnRelayOutput(@NonNull RelayOutputDto relayOutputDto, String ip) {
        String response = shellyProClient.setOutputState(
                URI.create(relayOutputDto.getStatusUrl()),
                relayOutputDto.getOutputId()
        );
        return parseToogleResponseAndUpdateState(relayOutputDto, response);
    }

    @Override
    public RelayOutputDto turnOffRelayOutput(@NonNull RelayOutputDto relayOutputDto, String ip) {
        return turnOnRelayOutput(relayOutputDto, ip);
    }

    private RelayOutputDto parseStatusResponseAndUpdateState(RelayOutputDto relayOutputDto, String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        ShellyProStatusOutputDto shellyProStatusOutputDto;
        try {
            shellyProStatusOutputDto = objectMapper.readValue(response, ShellyProStatusOutputDto.class);
        } catch (JsonProcessingException e) {
            shellyProStatusOutputDto = ShellyProStatusOutputDto.builder().state(null).build();
            log.error(PARSE_ERROR_MESSAGE, relayOutputDto.getStatusUrl());
        }

        OutputStatus outputStatus = outputStatusMapper.toOutputStatus(shellyProStatusOutputDto.getState());
        relayOutputDto.setOutputStatus(outputStatus);
        return relayOutputDto;
    }

    private RelayOutputDto parseToogleResponseAndUpdateState(RelayOutputDto relayOutputDto, String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        ShellyProToggleOutputDto shellyProToggleOutputDto;
        try {
            shellyProToggleOutputDto = objectMapper.readValue(response, ShellyProToggleOutputDto.class);
        } catch (JsonProcessingException e) {
            shellyProToggleOutputDto = ShellyProToggleOutputDto.builder().state(null).build();
            log.error(PARSE_ERROR_MESSAGE, relayOutputDto.getStatusUrl());
        }

        OutputStatus outputStatus = outputStatusMapper.toOutputStatusReverse(shellyProToggleOutputDto.getState());
        relayOutputDto.setOutputStatus(outputStatus);
        return relayOutputDto;
    }
}
