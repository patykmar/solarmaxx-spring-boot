package cz.patyk.solarmaxx.backend.adapter;

import cz.patyk.solarmaxx.backend.client.ShellyProClient;
import cz.patyk.solarmaxx.backend.dto.data.RelayOutputDataDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.dto.relay.output.shellypro.ShellyProStatusOutputDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.shellypro.ShellyProToggleOutputDto;
import cz.patyk.solarmaxx.backend.mapper.relay.OutputStatusMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class ShellyProRelayAdapter implements RelayAdapter {
    private final OutputStatusMapper outputStatusMapper;
    private final ShellyProClient shellyProClient;

    @Override
    public RelayOutputDataDto updateStatusFromRelay(@NonNull RelayOutputDataDto relayOutputDataDto) {
        String relayIpAddress = relayOutputDataDto.getRelayIpAddress();

        ShellyProStatusOutputDto shellyProStatusOutputDto = shellyProClient.getOutputStatusWithSpecificPortObject(
                AdapterUtils.createInsecureBasicUrl(relayIpAddress), relayOutputDataDto.getOutputId()
        );
        return parseStatusResponseAndUpdateState(relayOutputDataDto, shellyProStatusOutputDto);
    }

    @Override
    public RelayOutputDataDto turnOnRelayOutput(@NonNull RelayOutputDataDto relayOutputDataDto) {
        String relayIpAddress = relayOutputDataDto.getRelayIpAddress();

        ShellyProToggleOutputDto shellyProToggleOutputDto = shellyProClient.setOutputState(
                AdapterUtils.createInsecureBasicUrl(relayIpAddress), relayOutputDataDto.getOutputId()
        );
        return parseToogleResponseAndUpdateState(relayOutputDataDto, shellyProToggleOutputDto);
    }

    @Override
    public RelayOutputDataDto turnOffRelayOutput(@NonNull RelayOutputDataDto relayOutputDataDto) {
        return turnOnRelayOutput(relayOutputDataDto);
    }

    private RelayOutputDataDto parseStatusResponseAndUpdateState(RelayOutputDataDto relayOutputDataDto, ShellyProStatusOutputDto shellyProStatusOutputDto) {
        OutputStatus outputStatus = outputStatusMapper.toOutputStatus(shellyProStatusOutputDto.getState());
        relayOutputDataDto.setDeviceOutputStatus(outputStatus);
        return relayOutputDataDto;
    }

    private RelayOutputDataDto parseToogleResponseAndUpdateState(RelayOutputDataDto relayOutpuDatatDto, ShellyProToggleOutputDto shellyProToggleOutputDto) {
        OutputStatus outputStatus = outputStatusMapper.toOutputStatusReverse(shellyProToggleOutputDto.getState());
        relayOutpuDatatDto.setDeviceOutputStatus(outputStatus);
        return relayOutpuDatatDto;
    }

}
