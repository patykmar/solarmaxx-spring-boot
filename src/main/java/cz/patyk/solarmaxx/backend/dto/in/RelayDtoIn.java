package cz.patyk.solarmaxx.backend.dto.in;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelayDtoIn implements IDtoIn {
    private Long id;
    private Long userId;
    private Long relayTypeDtoId;
    private String ipAddress;
    private String name;
    private Short port;
    private Byte outputCount;
}
