package cz.patyk.solarmaxx.backend.dto.out;

import cz.patyk.solarmaxx.backend.dto.WeekDay;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
public class RelayScheduleDtoOut implements IDtoOut {
    private Long id;
    private Long relayId;
    private Byte outputId;
    private LocalTime onStart;
    private LocalTime onEnd;
    private Byte dayNumber;
    private WeekDay weekDay;
}
