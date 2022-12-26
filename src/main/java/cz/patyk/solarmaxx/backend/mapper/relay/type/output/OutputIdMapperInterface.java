package cz.patyk.solarmaxx.backend.mapper.relay.type.output;

import cz.patyk.solarmaxx.backend.dto.relay.output.RelayOutputDto;
import cz.patyk.solarmaxx.backend.entity.Relay;

import java.util.List;

public interface OutputIdMapperInterface {
    List<RelayOutputDto> getDeviceOutputs(Relay relay, boolean offlineMode);

    List<RelayOutputDto> getDeviceOutputs(Relay relay);
}
