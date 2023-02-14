package cz.patyk.solarmaxx.backend.dto;

import cz.patyk.solarmaxx.backend.dto.in.IDtoIn;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelayDto implements IDtoIn {
    private Long id;
    private String name;
    private String ipAddress;
    private Short port;
    private Long userId;
    private Long relayTypeId;
}
