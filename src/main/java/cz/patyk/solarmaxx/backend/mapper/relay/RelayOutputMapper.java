package cz.patyk.solarmaxx.backend.mapper.relay;

import cz.patyk.solarmaxx.backend.dto.RelayOutputDto;
import cz.patyk.solarmaxx.backend.dto.data.RelayOutputDataDto;
import cz.patyk.solarmaxx.backend.dto.relay.SupportedRelayType;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.entity.Relay;
import cz.patyk.solarmaxx.backend.entity.RelayOutput;
import cz.patyk.solarmaxx.backend.mapper.BasicDataMapper;
import cz.patyk.solarmaxx.backend.repository.RelayRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class RelayOutputMapper implements BasicDataMapper<RelayOutput, RelayOutputDto, RelayOutputDataDto> {
    @Autowired
    protected RelayRepository relayRepository;

    @Override
    @Mapping(target = "relayOutputSchedules", ignore = true)
    @Mapping(target = "outputStatus", expression = "java(getOutputStatus(dto.getOutputStatus()))")
    @Mapping(target = "relay", expression = "java(getRelayById(dto.getRelayId()))")
    public abstract RelayOutput dtoToEntity(RelayOutputDto dto);

    @Override
    @Mapping(target = "relayOutputSchedules", ignore = true)
    @Mapping(target = "relay", expression = "java(getRelayById(dataDto.getRelayId()))")
    public abstract RelayOutput dtoDataToEntity(RelayOutputDataDto dataDto);

    @Mapping(target = "relayOutputSchedules", ignore = true)
    @Mapping(target = "relay", expression = "java(getRelayById(dataDto.getRelayId()))")
    @Mapping(target = "outputStatus", source = "deviceOutputStatus")
    public abstract RelayOutput fromDeviceRelayOutputDataDtoToEntity(RelayOutputDataDto dataDto);

    @Override
    @Mapping(target = "deviceOutputStatus", ignore = true)
    @Mapping(target = "relayId", source = "entity.relay.id")
    @Mapping(target = "relayName", source = "entity.relay.name")
    @Mapping(target = "relayIpAddress", source = "entity.relay.ipAddress")
    @Mapping(target = "relayPort", source = "entity.relay.port")
    @Mapping(target = "relayTypeId", source = "entity.relay.relayType.id")
    @Mapping(target = "relayTypeName", source = "entity.relay.relayType.name")
    @Mapping(target = "relayTypeString", source = "entity.relay.relayType.deviceTypeString")
    @Mapping(target = "relayTypeEnum", expression = "java(toSupportedRelayType(entity))")
    public abstract RelayOutputDataDto entityToDataDto(RelayOutput entity);

    @Override
    @Mapping(target = "relayId", source = "entity.relay.id")
    public abstract RelayOutputDto entityToDto(RelayOutput entity);

    protected Relay getRelayById(Long id) {
        return relayRepository.findById(id)
                .orElse(new Relay());
    }

    protected OutputStatus getOutputStatus(String status) {
        return OutputStatus.fromString(status);
    }

    protected SupportedRelayType toSupportedRelayType(RelayOutput relayOutput) {
        return SupportedRelayType.fromString(relayOutput.getRelay().getRelayType().getDeviceTypeString());
    }
}
