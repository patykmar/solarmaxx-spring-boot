package cz.patyk.solarmaxx.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelayOutputScheduleDto implements DtoInterface {
    private Long id;
    private Long relayOutputId;
    private String onStart;
    private String onEnd;
    private Byte dayNumber;
}
