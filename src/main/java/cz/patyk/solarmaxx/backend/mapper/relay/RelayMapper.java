package cz.patyk.solarmaxx.backend.mapper.relay;

import cz.patyk.solarmaxx.backend.dto.RelayDto;
import cz.patyk.solarmaxx.backend.dto.RelayOutputDto;
import cz.patyk.solarmaxx.backend.dto.in.RelayDtoIn;
import cz.patyk.solarmaxx.backend.dto.out.RelayDtoOut;
import cz.patyk.solarmaxx.backend.dto.out.RelayScheduleDtoOut;
import cz.patyk.solarmaxx.backend.dto.out.RelayTypeDtoOut;
import cz.patyk.solarmaxx.backend.entity.Relay;
import cz.patyk.solarmaxx.backend.entity.RelayOutput;
import cz.patyk.solarmaxx.backend.entity.RelaySchedule;
import cz.patyk.solarmaxx.backend.entity.RelayType;
import cz.patyk.solarmaxx.backend.entity.User;
import cz.patyk.solarmaxx.backend.mapper.BasicMapper;
import cz.patyk.solarmaxx.backend.mapper.RelayScheduleMapper;
import cz.patyk.solarmaxx.backend.mapper.UserMapper;
import cz.patyk.solarmaxx.backend.mapper.relay.type.RelayTypeMapper;
import cz.patyk.solarmaxx.backend.service.RelayTypeService;
import cz.patyk.solarmaxx.backend.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, RelayTypeMapper.class, RelayScheduleMapper.class})
public abstract class RelayMapper implements BasicMapper<Relay, RelayDtoIn, RelayDtoOut> {

    @Autowired
    protected UserService userService;
    @Autowired
    protected RelayTypeService relayTypeService;
    @Autowired
    protected RelayTypeMapper relayTypeMapper;
    @Autowired
    protected RelayScheduleMapper relayScheduleMapper;
    @Autowired
    protected RelayOutputMapper relayOutputMapper;

    @Override
    @Mapping(target = "user", expression = "java(getUserEntity(dtoIn.getUserId()))")
    @Mapping(target = "relayType", expression = "java(getRelayType(dtoIn.getRelayTypeDtoId()))")
    @Mapping(target = "relaySchedules", ignore = true)
    @Mapping(target = "relayOutputs", ignore = true)
    public abstract Relay toEntity(RelayDtoIn dtoIn);

    @Mapping(target = "user", expression = "java(getUserEntity(relayDto.getUserId()))")
    @Mapping(target = "relayType", expression = "java(getRelayType(relayDto.getRelayTypeId()))")
    @Mapping(target = "relaySchedules", ignore = true)
    @Mapping(target = "relayOutputs", ignore = true)
    @Mapping(target = "outputCount", ignore = true)
    public abstract Relay toEntity(RelayDto relayDto);

    @Override
    @Mapping(target = "relayOutputDtos", expression = "java(getListRelayOutputDto(relay.getRelayOutputs()))")
    @Mapping(target = "relayTypeDtoOut", expression = "java(toRelayTypeDtoOut(relay.getRelayType()))")
    @Mapping(target = "relaySchedulesOuts", expression = "java(getRelaySchedulesOuts(relay.getRelaySchedules()))")
    public abstract RelayDtoOut toDtoOut(Relay relay);

    @Mapping(target = "relayOutputDtos", expression = "java(getListRelayOutputDto(relay.getRelayOutputs()))")
    @Mapping(target = "relayTypeDtoOut", expression = "java(toRelayTypeDtoOut(relay.getRelayType()))")
    @Mapping(target = "relaySchedulesOuts", expression = "java(getRelaySchedulesOuts(relay.getRelaySchedules()))")
    public abstract RelayDtoOut toDtoOutOnLineMode(Relay relay);

    @Mapping(target = "userId", source = "relay.user.id")
    @Mapping(target = "relayTypeId", source = "relay.relayType.id")
    public abstract RelayDto entityToDto(Relay relay);

    public User getUserEntity(Long id) {
        return userService.getOneEntity(id);
    }

    public RelayType getRelayType(Long id) {
        return relayTypeService.getOneEntity(id);
    }

    public RelayTypeDtoOut toRelayTypeDtoOut(RelayType relayType) {
        return relayTypeMapper.toDtoOut(relayType);
    }

    protected List<RelayScheduleDtoOut> getRelaySchedulesOuts(List<RelaySchedule> relaySchedules) {
        return relaySchedules.stream()
                .map(relayScheduleMapper::toDtoOut)
                .toList();
    }

    protected List<RelayOutputDto> getListRelayOutputDto(List<RelayOutput> relayOutputs) {
        return relayOutputs.stream()
                .map(relayOutputMapper::entityToDto)
                .toList();
    }

}
