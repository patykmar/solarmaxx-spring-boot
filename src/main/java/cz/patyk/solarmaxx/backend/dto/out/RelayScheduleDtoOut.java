package cz.patyk.solarmaxx.backend.dto.out;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RelayScheduleDtoOut implements IDtoOut {
    private Long id;
    private RelayDtoOut relayDtoOut;
    private Date onStart;
    private Date onEnd;
    private Byte dayNumber;
}
