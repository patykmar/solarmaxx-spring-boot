package cz.patyk.solarmaxx.backend.dto.out;

import cz.patyk.solarmaxx.backend.dto.relay.output.RelayOutputDto;
import lombok.Data;

import java.util.List;

@Data
public class RelayDtoOut {
    private Long id;
    private UserDtoOut user;
    private RelayTypeDtoOut relayTypeDtoOut;
    private String name;
    private String ipAddress;
    private Short port;
    private Byte outputCount;
    private List<RelayOutputDto> relayOutputDtos;
}