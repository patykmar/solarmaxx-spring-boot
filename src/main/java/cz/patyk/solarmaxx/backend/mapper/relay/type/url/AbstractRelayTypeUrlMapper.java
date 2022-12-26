package cz.patyk.solarmaxx.backend.mapper.relay.type.url;

import cz.patyk.solarmaxx.backend.config.DeviceTypeUrlPattern;
import cz.patyk.solarmaxx.backend.dto.relay.type.url.parameter.StatusUrlParameter;
import cz.patyk.solarmaxx.backend.dto.relay.type.url.parameter.ToggleUrlParameter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@RequiredArgsConstructor
public class AbstractRelayTypeUrlMapper implements RelayTypeUrlMapper {
    protected final DeviceTypeUrlPattern deviceTypeUrlPattern;
    protected Byte defaultOutputId = 0;

    @Override
    public String toRealUrlStatus(StatusUrlParameter statusUrlParameter) {
        if (Objects.isNull(statusUrlParameter.getOutputId())) {
            statusUrlParameter.setOutputId(defaultOutputId);
        }

        String[] searchList = {deviceTypeUrlPattern.getIp(), deviceTypeUrlPattern.getId(), deviceTypeUrlPattern.getPort()};
        String[] replacementList = {statusUrlParameter.getIp(), String.valueOf(statusUrlParameter.getOutputId()), String.valueOf(statusUrlParameter.getPort())};

        return StringUtils.replaceEach(
                statusUrlParameter.getTemplate(),
                searchList,
                replacementList
        );
    }

    @Override
    public String toRealUrlToggle(ToggleUrlParameter toggleUrlParameter) {
        if (Objects.isNull(toggleUrlParameter.getOutputId())) {
            toggleUrlParameter.setOutputId(defaultOutputId);
        }

        String[] searchList = {deviceTypeUrlPattern.getIp(), deviceTypeUrlPattern.getId(), deviceTypeUrlPattern.getPort(), deviceTypeUrlPattern.getToggle()};
        String[] replacementList = {toggleUrlParameter.getIp(), String.valueOf(toggleUrlParameter.getOutputId()), String.valueOf(toggleUrlParameter.getPort()), toggleUrlParameter.getToggle()};

        return StringUtils.replaceEach(
                toggleUrlParameter.getTemplate(),
                searchList,
                replacementList
        );
    }
}
