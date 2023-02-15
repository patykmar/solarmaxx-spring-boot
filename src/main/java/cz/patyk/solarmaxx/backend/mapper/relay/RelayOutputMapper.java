package cz.patyk.solarmaxx.backend.mapper.relay;

import cz.patyk.solarmaxx.backend.dto.RelayDto;
import cz.patyk.solarmaxx.backend.dto.RelayOutputDto;
import cz.patyk.solarmaxx.backend.dto.data.RelayOutputDataDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.entity.Relay;
import cz.patyk.solarmaxx.backend.entity.RelayOutput;
import cz.patyk.solarmaxx.backend.mapper.BasicDataMapper;
import cz.patyk.solarmaxx.backend.service.RelayService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class RelayOutputMapper implements BasicDataMapper<RelayOutput, RelayOutputDto, RelayOutputDataDto> {

    @Autowired
    protected RelayService relayService;

    @Override
    @Mapping(target = "outputStatus", expression = "java(getOutputStatus(dto.getOutputStatus()))")
    @Mapping(target = "relay", expression = "java(getRelayById(dto.getRelayId()))")
    public abstract RelayOutput dtoToEntity(RelayOutputDto dto);

    @Override
    @Mapping(target = "relay", expression = "java(getRelayById(dataDto.getRelayDto().getId()))")
    public abstract RelayOutput dtoDataToEntity(RelayOutputDataDto dataDto);

    @Override
    @Mapping(target = "relayDto", expression = "java(getRelayDtoById(entity.getRelay()))")
    public abstract RelayOutputDataDto entityToDataDto(RelayOutput entity);

    @Override
    @Mapping(target = "relayId", source = "entity.relay.id")
    public abstract RelayOutputDto entityToDto(RelayOutput entity);

    protected Relay getRelayById(Long id) {
        return relayService.getOneEntity(id);
    }

    protected RelayDto getRelayDtoById(Relay relay) {
        return relayService.getOneDto(relay);
    }

    protected OutputStatus getOutputStatus(String status) {
        return OutputStatus.fromString(status);
    }
}
