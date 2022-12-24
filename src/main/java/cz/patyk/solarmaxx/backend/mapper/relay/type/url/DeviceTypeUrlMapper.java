package cz.patyk.solarmaxx.backend.mapper.relay.type.url;

import cz.patyk.solarmaxx.backend.dto.relay.type.url.parameter.StatusUrlParameter;
import cz.patyk.solarmaxx.backend.dto.relay.type.url.parameter.ToggleUrlParameter;

public interface DeviceTypeUrlMapper {
    String toRealUrlStatus(StatusUrlParameter statusUrlParameter);
    String toRealUrlToggle(ToggleUrlParameter toggleUrlParameter);
}
