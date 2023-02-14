package cz.patyk.solarmaxx.backend.mapper.relay;

import cz.patyk.solarmaxx.backend.dto.RelayDto;
import cz.patyk.solarmaxx.backend.dto.in.RelayDtoIn;
import cz.patyk.solarmaxx.backend.dto.out.RelayDtoOut;
import cz.patyk.solarmaxx.backend.dto.out.RelayScheduleDtoOut;
import cz.patyk.solarmaxx.backend.dto.out.RelayTypeDtoOut;
import cz.patyk.solarmaxx.backend.dto.relay.SupportedRelayType;
import cz.patyk.solarmaxx.backend.dto.relay.output.RelayOutputDto;
import cz.patyk.solarmaxx.backend.dto.relay.type.url.parameter.StatusUrlParameter;
import cz.patyk.solarmaxx.backend.dto.relay.type.url.parameter.ToggleUrlParameter;
import cz.patyk.solarmaxx.backend.entity.Relay;
import cz.patyk.solarmaxx.backend.entity.RelaySchedule;
import cz.patyk.solarmaxx.backend.entity.RelayType;
import cz.patyk.solarmaxx.backend.entity.User;
import cz.patyk.solarmaxx.backend.factory.mapper.RelayOutputIdFactory;
import cz.patyk.solarmaxx.backend.mapper.BasicMapper;
import cz.patyk.solarmaxx.backend.mapper.RelayScheduleMapper;
import cz.patyk.solarmaxx.backend.mapper.UserMapper;
import cz.patyk.solarmaxx.backend.mapper.relay.type.RelayTypeMapper;
import cz.patyk.solarmaxx.backend.mapper.relay.type.output.OutputIdMapper;
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
    protected RelayOutputIdFactory relayOutputIdFactory;
    @Autowired
    protected RelayScheduleMapper relayScheduleMapper;

    @Override
    @Mapping(target = "user", expression = "java(getUserEntity(dtoIn.getUserId()))")
    @Mapping(target = "relayType", expression = "java(getRelayType(dtoIn.getRelayTypeDtoId()))")
    @Mapping(target = "relaySchedules", ignore = true)
    @Mapping(target = "relayOutputs", ignore = true)
    public abstract Relay toEntity(RelayDtoIn dtoIn);

    @Override
    @Mapping(target = "relayOutputDtos", expression = "java(getDeviceOutputs(relay, false))")
    @Mapping(target = "relayTypeDtoOut", expression = "java(toRelayTypeDtoOut(relay.getRelayType()))")
    @Mapping(target = "relaySchedulesOuts", expression = "java(getRelaySchedulesOuts(relay.getRelaySchedules()))")
    public abstract RelayDtoOut toDtoOut(Relay relay);

    @Mapping(target = "relayOutputDtos", expression = "java(getDeviceOutputs(relay, true))")
    @Mapping(target = "relayTypeDtoOut", expression = "java(toRelayTypeDtoOut(relay.getRelayType()))")
    @Mapping(target = "relaySchedulesOuts", expression = "java(getRelaySchedulesOuts(relay.getRelaySchedules()))")
    public abstract RelayDtoOut toDtoOutOnLineMode(Relay relay);

    @Mapping(target = "outputId", ignore = true)
    @Mapping(target = "urlTemplate", source = "relay.relayType.urlStatus")
    public abstract StatusUrlParameter toStatusUrlParameter(Relay relay);

    @Mapping(target = "toggle", ignore = true)
    @Mapping(target = "outputId", ignore = true)
    @Mapping(target = "urlTemplate", source = "relay.relayType.urlToggle")
    public abstract ToggleUrlParameter toToggleUrlParameter(Relay relay);

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

    public List<RelayOutputDto> getDeviceOutputs(Relay relay, boolean onlineMode) {
        SupportedRelayType supportedRelayType = SupportedRelayType.fromString(relay.getRelayType().getDeviceTypeString());
        OutputIdMapper outputIdMapper = relayOutputIdFactory.getOutputIdMapper(supportedRelayType);
        return outputIdMapper.getDeviceOutputs(relay, onlineMode);
    }

    protected List<RelayScheduleDtoOut> getRelaySchedulesOuts(List<RelaySchedule> relaySchedules) {
        return relaySchedules.stream()
                .map(relayScheduleMapper::toDtoOut)
                .toList();
    }

}
