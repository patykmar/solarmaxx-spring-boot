package cz.patyk.solarmaxx.backend.adapter;

import cz.patyk.solarmaxx.backend.dto.relay.output.RelayOutputDto;
import lombok.NonNull;

public interface RelayAdapter {
    RelayOutputDto updateStatusFromRelay(@NonNull RelayOutputDto relayOutputDto);

    RelayOutputDto turnOnRelayOutput(@NonNull RelayOutputDto relayOutputDto);

    RelayOutputDto turnOffRelayOutput(@NonNull RelayOutputDto relayOutputDto);
}
