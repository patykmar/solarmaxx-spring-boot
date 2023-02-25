package cz.patyk.solarmaxx.backend.dto.out;

import cz.patyk.solarmaxx.backend.dto.RelayOutputDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RelayDtoOut implements IDtoOut {
    private Long id;
    private UserDtoOut user;
    private RelayTypeDtoOut relayTypeDtoOut;
    private String name;
    private String ipAddress;
    private Short port;
    private Byte outputCount;
    private List<RelayOutputDto> relayOutputDtos;
    private List<RelayScheduleDtoOut> relaySchedulesOuts;
}
