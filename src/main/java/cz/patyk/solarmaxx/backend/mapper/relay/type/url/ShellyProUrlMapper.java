package cz.patyk.solarmaxx.backend.mapper.relay.type.url;

import cz.patyk.solarmaxx.backend.config.DeviceTypeUrlPattern;
import cz.patyk.solarmaxx.backend.dto.relay.type.url.parameter.ToggleUrlParameter;

public class ShellyProUrlMapper extends AbstractRelayTypeUrlMapper {
    public ShellyProUrlMapper(DeviceTypeUrlPattern deviceTypeUrlPattern) {
        super(deviceTypeUrlPattern);
        this.defaultOutputId = 0;
    }

    @Override
    public String toRealUrlToggle(ToggleUrlParameter toggleUrlParameter) {
        return super.toRealUrlStatus(toggleUrlParameter);
    }
}
