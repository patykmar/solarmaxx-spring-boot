package cz.patyk.solarmaxx.backend.dto.device.type.url.parameter;

import lombok.Data;

@Data
public class StatusUrlParameter {
    private String template;
    private String ip;
    private Short port;
    private Byte outputId;
}
