package cz.patyk.solarmaxx.backend.mapper.relay.type.url;

import cz.patyk.solarmaxx.backend.dto.relay.type.url.RelayTypeUrlPatternDto;
import cz.patyk.solarmaxx.backend.dto.relay.type.url.parameter.StatusUrlParameter;
import cz.patyk.solarmaxx.backend.dto.relay.type.url.parameter.ToggleUrlParameter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@RequiredArgsConstructor
public abstract class AbstractRelayTypeUrlMapper implements RelayTypeUrlMapper {
    protected final RelayTypeUrlPatternDto relayTypeUrlPatternDto;
    protected Byte defaultOutputId = 0;

    @Override
    public String toRealUrlStatus(StatusUrlParameter statusUrlParameter) {
        if (Objects.isNull(statusUrlParameter.getOutputId())) {
            statusUrlParameter.setOutputId(defaultOutputId);
        }

        String[] searchList = {relayTypeUrlPatternDto.getIp(), relayTypeUrlPatternDto.getId(), relayTypeUrlPatternDto.getPort()};
        String[] replacementList = {statusUrlParameter.getIpAddress(), String.valueOf(statusUrlParameter.getOutputId()), String.valueOf(statusUrlParameter.getPort())};

        return StringUtils.replaceEach(
                statusUrlParameter.getUrlTemplate(),
                searchList,
                replacementList
        );
    }

    @Override
    public String toRealUrlToggle(ToggleUrlParameter toggleUrlParameter) {
        if (Objects.isNull(toggleUrlParameter.getOutputId())) {
            toggleUrlParameter.setOutputId(defaultOutputId);
        }

        String[] searchList = {relayTypeUrlPatternDto.getIp(), relayTypeUrlPatternDto.getId(), relayTypeUrlPatternDto.getPort(), relayTypeUrlPatternDto.getToggle()};
        String[] replacementList = {toggleUrlParameter.getIpAddress(), String.valueOf(toggleUrlParameter.getOutputId()), String.valueOf(toggleUrlParameter.getPort()), toggleUrlParameter.getToggle()};

        return StringUtils.replaceEach(
                toggleUrlParameter.getUrlTemplate(),
                searchList,
                replacementList
        );
    }
}
