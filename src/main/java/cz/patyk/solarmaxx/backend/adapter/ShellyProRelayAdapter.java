package cz.patyk.solarmaxx.backend.adapter;

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
    private static final String HTTP = "http://";
    private final OutputStatusMapper outputStatusMapper;
    private final ShellyProClient shellyProClient;

    @Override
    public RelayOutputDto updateStatusFromRelay(@NonNull RelayOutputDto relayOutputDto, String ip) {
        ShellyProStatusOutputDto shellyProStatusOutputDto = shellyProClient.getOutputStatusWithSpecificPortObject(
                createBasicUrl(ip), relayOutputDto.getOutputId()
        );
        return parseStatusResponseAndUpdateState(relayOutputDto, shellyProStatusOutputDto);
    }

    @Override
    public RelayOutputDto turnOnRelayOutput(@NonNull RelayOutputDto relayOutputDto, String ip) {
        ShellyProToggleOutputDto shellyProToggleOutputDto = shellyProClient.setOutputState(
                createBasicUrl(ip), relayOutputDto.getOutputId()
        );
        return parseToogleResponseAndUpdateState(relayOutputDto, shellyProToggleOutputDto);
    }

    @Override
    public RelayOutputDto turnOffRelayOutput(@NonNull RelayOutputDto relayOutputDto, String ip) {
        return turnOnRelayOutput(relayOutputDto, ip);
    }

    private RelayOutputDto parseStatusResponseAndUpdateState(RelayOutputDto relayOutputDto, ShellyProStatusOutputDto shellyProStatusOutputDto) {
        OutputStatus outputStatus = outputStatusMapper.toOutputStatus(shellyProStatusOutputDto.getState());
        relayOutputDto.setOutputStatus(outputStatus);
        return relayOutputDto;
    }

    private RelayOutputDto parseToogleResponseAndUpdateState(RelayOutputDto relayOutputDto, ShellyProToggleOutputDto shellyProToggleOutputDto) {
        OutputStatus outputStatus = outputStatusMapper.toOutputStatusReverse(shellyProToggleOutputDto.getState());
        relayOutputDto.setOutputStatus(outputStatus);
        return relayOutputDto;
    }

    private URI createBasicUrl(String ip) {
        return URI.create(HTTP + ip);
    }
}
