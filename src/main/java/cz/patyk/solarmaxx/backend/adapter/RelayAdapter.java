package cz.patyk.solarmaxx.backend.adapter;

import cz.patyk.solarmaxx.backend.dto.data.RelayOutputDataDto;
import lombok.NonNull;

public interface RelayAdapter {
    RelayOutputDataDto updateStatusFromRelay(@NonNull RelayOutputDataDto relayOutputDto);

    RelayOutputDataDto turnOnRelayOutput(@NonNull RelayOutputDataDto relayOutputDto);

    RelayOutputDataDto turnOffRelayOutput(@NonNull RelayOutputDataDto relayOutputDto);
}
