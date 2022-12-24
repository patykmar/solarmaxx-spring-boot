package cz.patyk.solarmaxx.backend.mapper.relay;

import cz.patyk.solarmaxx.backend.dto.in.RelayDtoIn;
import cz.patyk.solarmaxx.backend.dto.out.RelayDtoOut;
import cz.patyk.solarmaxx.backend.entity.Relay;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class DeviceMapper {
    public abstract RelayDtoOut toDtoOut(Relay relay);

    public abstract Relay toEntityDevice(RelayDtoIn relayDtoIn);
}
