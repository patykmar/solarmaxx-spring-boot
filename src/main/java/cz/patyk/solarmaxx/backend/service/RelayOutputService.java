package cz.patyk.solarmaxx.backend.service;

import cz.patyk.solarmaxx.backend.adapter.RelayAdapter;
import cz.patyk.solarmaxx.backend.dto.out.RelayDtoOut;
import cz.patyk.solarmaxx.backend.dto.relay.SupportedRelayType;
import cz.patyk.solarmaxx.backend.dto.relay.output.RelayOutputDto;
import cz.patyk.solarmaxx.backend.exceptions.ApplicationException;
import cz.patyk.solarmaxx.backend.factory.adapter.RelayAdapterFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RelayOutputService {

    private final RelayService relayService;
    private final RelayAdapterFactory relayAdapterFactory;

    public RelayOutputDto toggleOutput(Long relayId, Byte outputId, boolean toggle) {
        RelayDtoOut serviceOne = relayService.getOne(relayId);
        SupportedRelayType relayType = SupportedRelayType.fromString(serviceOne.getRelayTypeDtoOut().getDeviceTypeString());
        RelayAdapter relayAdapter = relayAdapterFactory.getRelayAdapter(relayType);

        RelayOutputDto relayOutputDto = serviceOne.getRelayOutputDtos().stream()
                .filter(output -> Objects.equals(output.getOutputId(), outputId))
                .findFirst()
                .orElseThrow(() -> new ApplicationException("Relay output with ID " + outputId + " not found", HttpStatus.NOT_FOUND));

        if (toggle) {
            return relayAdapter.turnOnRelayOutput(relayOutputDto, serviceOne.getIpAddress());
        } else {
            return relayAdapter.turnOffRelayOutput(relayOutputDto, serviceOne.getIpAddress());
        }

    }

}
