package cz.patyk.solarmaxx.backend.dto.in;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RelayScheduleDtoIn implements IDtoIn {
    private Long id;
    private Long relayId;
    private Date onStart;
    private Date onEnd;
    private Byte dayNumber;
}
