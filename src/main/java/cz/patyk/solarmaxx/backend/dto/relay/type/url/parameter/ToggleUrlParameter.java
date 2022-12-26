package cz.patyk.solarmaxx.backend.dto.relay.type.url.parameter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ToggleUrlParameter extends StatusUrlParameter {
    private String toggle;
}
