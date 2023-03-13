package cz.patyk.solarmaxx.backend.dto.data;

import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.model.WeekDayModel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

@Data
@Builder
public class RelayOutputScheduleDataDto implements DtoDataInterface {
    private Long id;
    private LocalTime onStart;
    private LocalTime onEnd;
    private WeekDayModel.WEEK_DAY weekDay;
    private Long relayOutputId;
    private String relayOutputDescription;
    private OutputStatus relayOutputStatus;
    private Long relayId;
}
