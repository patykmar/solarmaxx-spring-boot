package cz.patyk.solarmaxx.backend.mapper.relay;

import cz.patyk.solarmaxx.backend.dto.in.RelayDtoIn;
import cz.patyk.solarmaxx.backend.dto.out.RelayDtoOut;
import cz.patyk.solarmaxx.backend.entity.Relay;
import cz.patyk.solarmaxx.backend.mapper.BasicMapper;
import org.mapstruct.Mapper;

@Mapper()
public abstract class RelayMapper implements BasicMapper<Relay, RelayDtoIn, RelayDtoOut> {

}
