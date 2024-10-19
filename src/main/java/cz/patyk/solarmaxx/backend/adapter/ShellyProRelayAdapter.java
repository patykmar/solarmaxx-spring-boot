package cz.patyk.solarmaxx.backend.adapter;

import cz.patyk.solarmaxx.backend.client.ShellyProClient;
import cz.patyk.solarmaxx.backend.dto.data.RelayOutputDataDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.dto.relay.output.shellypro.ShellyProStatusOutputDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.shellypro.ShellyProToggleOutputDto;
import cz.patyk.solarmaxx.backend.mapper.relay.OutputStatusMapper;
import cz.patyk.solarmaxx.backend.service.AdapterService;
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
    private final AdapterService adapterService;

    @Override
    public RelayOutputDataDto updateStatusFromRelay(@NonNull RelayOutputDataDto relayOutputDataDto) {
        ShellyProStatusOutputDto shellyProStatusOutputDto = shellyProClient.getOutputStatusWithSpecificPortObject(
                adapterService.createInsecureBasicUrl(relayOutputDataDto), relayOutputDataDto.getOutputId()
        );
        return parseStatusResponseAndUpdateState(relayOutputDataDto, shellyProStatusOutputDto);
    }

    @Override
    public RelayOutputDataDto turnOnRelayOutput(@NonNull RelayOutputDataDto relayOutputDataDto) {
        ShellyProToggleOutputDto shellyProToggleOutputDto = shellyProClient.setOutputState(
                adapterService.createInsecureBasicUrl(relayOutputDataDto),
                relayOutputDataDto.getOutputId(),
                adapterService.convertOutputStatusToBoolean(relayOutputDataDto.getOutputStatus())
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
