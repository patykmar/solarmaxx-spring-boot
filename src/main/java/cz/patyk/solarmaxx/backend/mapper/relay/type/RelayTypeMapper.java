package cz.patyk.solarmaxx.backend.mapper.relay.type;

import cz.patyk.solarmaxx.backend.dto.in.RelayTypeDtoIn;
import cz.patyk.solarmaxx.backend.dto.out.RelayTypeDtoOut;
import cz.patyk.solarmaxx.backend.entity.RelayType;
import cz.patyk.solarmaxx.backend.mapper.BasicMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RelayTypeMapper extends BasicMapper<RelayType, RelayTypeDtoIn, RelayTypeDtoOut> {

    @Override
    @Mapping(source = "urlStatus", target = "urlStatusTemplate")
    @Mapping(source = "urlToggle", target = "urlToggleTemplate")
    RelayTypeDtoOut toDtoOut(RelayType entity);

    @Mapping(target = "relays", ignore = true)
    RelayType toEntity(RelayTypeDtoIn relayTypeDtoIn);

}
