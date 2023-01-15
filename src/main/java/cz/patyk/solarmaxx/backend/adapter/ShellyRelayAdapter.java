package cz.patyk.solarmaxx.backend.adapter;

import cz.patyk.solarmaxx.backend.dto.relay.output.RelayOutputDto;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ShellyRelayAdapter implements RelayAdapter {
    @Override
    public RelayOutputDto updateStatusFromRelay(@NonNull RelayOutputDto relayOutputDto) {
        return null;
    }

    @Override
    public RelayOutputDto turnOnRelayOutput(@NonNull RelayOutputDto relayOutputDto) {
        return null;
    }

    @Override
    public RelayOutputDto turnOffRelayOutput(@NonNull RelayOutputDto relayOutputDto) {
        return null;
    }
}
