package cz.patyk.solarmaxx.backend.mapper.relay.type.output;

import cz.patyk.solarmaxx.backend.dto.relay.output.RelayOutputDto;
import cz.patyk.solarmaxx.backend.entity.Relay;

import java.util.List;

public class ShellyProOutputIdMapper implements OutputIdMapperInterface {
    @Override
    public List<RelayOutputDto> getDeviceOutputs(Relay relay, boolean offlineMode) {
        return null;
    }

    @Override
    public List<RelayOutputDto> getDeviceOutputs(Relay relay) {
        return getDeviceOutputs(relay, false);
    }
}
