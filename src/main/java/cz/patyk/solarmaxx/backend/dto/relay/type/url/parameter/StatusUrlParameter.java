package cz.patyk.solarmaxx.backend.dto.relay.type.url.parameter;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class StatusUrlParameter {
    private String urlTemplate;
    private String ipAddress;
    private Short port;
    private Byte outputId;
}
