package cz.patyk.solarmaxx.backend.dto.out;

import cz.patyk.solarmaxx.backend.dto.WeekDay;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelayScheduleDtoOut implements IDtoOut {
    private Long id;
    private Long relayId;
    private Byte outputId;
    private String onStart;
    private String onEnd;
    private Byte dayNumber;
    private WeekDay weekDay;
}
