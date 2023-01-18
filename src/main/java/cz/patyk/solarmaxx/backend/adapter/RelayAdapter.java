package cz.patyk.solarmaxx.backend.adapter;

import cz.patyk.solarmaxx.backend.dto.relay.output.RelayOutputDto;
import lombok.NonNull;

public interface RelayAdapter {
    RelayOutputDto updateStatusFromRelay(@NonNull RelayOutputDto relayOutputDto, String ip);

    RelayOutputDto turnOnRelayOutput(@NonNull RelayOutputDto relayOutputDto, String ip);

    RelayOutputDto turnOffRelayOutput(@NonNull RelayOutputDto relayOutputDto, String ip);
}
