package cz.patyk.solarmaxx.backend.mapper.relay.type.output;

import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.dto.relay.output.RelayOutputDto;
import cz.patyk.solarmaxx.backend.dto.relay.type.url.parameter.StatusUrlParameter;
import cz.patyk.solarmaxx.backend.dto.relay.type.url.parameter.ToggleUrlParameter;
import cz.patyk.solarmaxx.backend.entity.Relay;
import cz.patyk.solarmaxx.backend.mapper.relay.type.url.RelayTypeUrlMapper;
import cz.patyk.solarmaxx.backend.mapper.relay.type.url.UrlParameterMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class OutputIdMapper implements OutputIdMapperInterface {
    private final UrlParameterMapper urlParameterMapper;
    private final RelayTypeUrlMapper relayTypeUrlMapper;

    protected RelayOutputDto toRelayOutputDto(Relay relay, Byte outputId) {
        StatusUrlParameter statusUrlParameter = urlParameterMapper.toStatusUrlParameters(relay, outputId);
        ToggleUrlParameter toggleUrlParameterOn = urlParameterMapper.toOnUrlParameters(relay, outputId);
        ToggleUrlParameter toggleUrlParameterOff = urlParameterMapper.toOffUrlParameters(relay, outputId);

        return RelayOutputDto.builder()
                .outputId(outputId)
                .outputStatus(OutputStatus.NA)
                .statusUrl(relayTypeUrlMapper.toRealUrlStatus(statusUrlParameter))
                .turnOnUrl(relayTypeUrlMapper.toRealUrlToggle(toggleUrlParameterOn))
                .turnOffUrl(relayTypeUrlMapper.toRealUrlToggle(toggleUrlParameterOff))
                .build();
    }

}
