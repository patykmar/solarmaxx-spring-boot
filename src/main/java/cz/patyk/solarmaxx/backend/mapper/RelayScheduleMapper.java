package cz.patyk.solarmaxx.backend.mapper;


import cz.patyk.solarmaxx.backend.dto.in.RelayScheduleDtoIn;
import cz.patyk.solarmaxx.backend.dto.out.RelayScheduleDtoOut;
import cz.patyk.solarmaxx.backend.entity.Relay;
import cz.patyk.solarmaxx.backend.entity.RelaySchedule;
import cz.patyk.solarmaxx.backend.exceptions.ApplicationException;
import cz.patyk.solarmaxx.backend.repository.RelayRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@Mapper(componentModel = "spring")
public abstract class RelayScheduleMapper implements BasicMapper<RelaySchedule, RelayScheduleDtoIn, RelayScheduleDtoOut> {
    @Autowired
    protected RelayRepository relayRepository;

    @Override
    @Mapping(target = "relay", expression = "java(getRelay(dtoIn.getRelayId()))")
    @Mapping(target = "dayNumber", expression = "java(dtoIn.getDayNumber())")
    public abstract RelaySchedule toEntity(RelayScheduleDtoIn dtoIn);

    @Override
    @Mapping(target = "relayId", source = "entity.relay.id")
    public abstract RelayScheduleDtoOut toDtoOut(RelaySchedule entity);

    protected Relay getRelay(Long id) {
        return relayRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("Cannot find relay with ID " + id, HttpStatus.NOT_FOUND));
    }
}
