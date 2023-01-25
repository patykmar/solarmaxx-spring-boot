package cz.patyk.solarmaxx.backend.dto.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelayScheduleDtoIn implements IDtoIn {
    private Long id;
    private Long relayId;
    private Byte outputId;
    private String onStart;
    private String onEnd;
    private Byte dayNumber;
}
