package cz.patyk.solarmaxx.backend.dto.in;

import lombok.Data;

@Data
public class RelayDtoIn {
    private Long id;
    private Long userId;
    private Long deviceTypeDtoId;
    private String ipAddress;
    private Short port;
    private Byte outputCount;
}
