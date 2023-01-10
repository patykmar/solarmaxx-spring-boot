package cz.patyk.solarmaxx.backend.mapper;


import cz.patyk.solarmaxx.backend.dto.in.RelayScheduleDtoIn;
import cz.patyk.solarmaxx.backend.dto.out.RelayDtoOut;
import cz.patyk.solarmaxx.backend.dto.out.RelayScheduleDtoOut;
import cz.patyk.solarmaxx.backend.entity.Relay;
import cz.patyk.solarmaxx.backend.entity.RelaySchedule;
import cz.patyk.solarmaxx.backend.mapper.relay.RelayMapper;
import cz.patyk.solarmaxx.backend.service.RelayService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {RelayMapper.class})
public abstract class RelayScheduleMapper implements BasicMapper<RelaySchedule, RelayScheduleDtoIn, RelayScheduleDtoOut> {
    @Autowired
    protected RelayMapper relayMapper;
    @Autowired
    protected RelayService relayService;

    @Override
    @Mapping(target = "relay", expression = "java(getRelay(dtoIn.getRelayId()))")
    @Mapping(target = "dayNumber", expression = "java(dtoIn.getDayNumber())")
    public abstract RelaySchedule toEntity(RelayScheduleDtoIn dtoIn);

    @Override
    @Mapping(target = "relayDtoOut", expression = "java(getRelayDtoOut(entity.getRelay()))")
    public abstract RelayScheduleDtoOut toDtoOut(RelaySchedule entity);

    protected RelayDtoOut getRelayDtoOut(Relay relay) {
        return relayMapper.toDtoOut(relay);
    }

    protected Relay getRelay(Long id) {
        return relayService.getOneEntity(id);
    }
}
