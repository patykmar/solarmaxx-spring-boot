package cz.patyk.solarmaxx.backend.mapper.relay.type.url;

import cz.patyk.solarmaxx.backend.dto.relay.type.url.parameter.StatusUrlParameter;
import cz.patyk.solarmaxx.backend.dto.relay.type.url.parameter.ToggleUrlParameter;
import cz.patyk.solarmaxx.backend.entity.Relay;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UrlParameterMapper {
    @Mapping(target = "urlTemplate", source = "relay.relayType.urlStatus")
    StatusUrlParameter toStatusUrlParameters(Relay relay, Byte outputId);

    @Mapping(target = "toggle", source = "relay.relayType.turnOn")
    @Mapping(target = "urlTemplate", source = "relay.relayType.urlToggle")
    ToggleUrlParameter toOnUrlParameters(Relay relay, Byte outputId);

    @Mapping(target = "toggle", source = "relay.relayType.turnOff")
    @Mapping(target = "urlTemplate", source = "relay.relayType.urlToggle")
    ToggleUrlParameter toOffUrlParameters(Relay relay, Byte outputId);
}
