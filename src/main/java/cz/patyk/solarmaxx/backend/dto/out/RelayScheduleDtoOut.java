package cz.patyk.solarmaxx.backend.dto.out;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelayScheduleDtoOut implements IDtoOut {
    private Long id;
    private RelayDtoOut relayDtoOut;
    private String onStart;
    private String onEnd;
    private Byte dayNumber;
}
