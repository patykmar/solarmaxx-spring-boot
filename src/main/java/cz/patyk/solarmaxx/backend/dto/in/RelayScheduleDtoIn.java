package cz.patyk.solarmaxx.backend.dto.in;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RelayScheduleDtoIn {
    private Long id;
    private Long relayId;
    private LocalDateTime onStart;
    private LocalDateTime onEnd;
    private Byte dayNumber;
}
