package cz.patyk.solarmaxx.backend.mapper.device.type.url;

import cz.patyk.solarmaxx.backend.dto.device.type.url.parameter.StatusUrlParameter;
import cz.patyk.solarmaxx.backend.dto.device.type.url.parameter.ToggleUrlParameter;

public interface DeviceTypeUrlMapper {
    String toRealUrlStatus(StatusUrlParameter statusUrlParameter);
    String toRealUrlToggle(ToggleUrlParameter toggleUrlParameter);
}
