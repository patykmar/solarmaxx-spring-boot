package cz.patyk.solarmaxx.backend.mapper;


import cz.patyk.solarmaxx.backend.dto.WeekDay;
import cz.patyk.solarmaxx.backend.dto.in.RelayScheduleDtoIn;
import cz.patyk.solarmaxx.backend.dto.out.RelayScheduleDtoOut;
import cz.patyk.solarmaxx.backend.entity.Relay;
import cz.patyk.solarmaxx.backend.entity.RelaySchedule;
import cz.patyk.solarmaxx.backend.exceptions.ApplicationException;
import cz.patyk.solarmaxx.backend.model.WeekDayModel;
import cz.patyk.solarmaxx.backend.repository.RelayRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.LocalTime;

@Mapper(componentModel = "spring")
public abstract class RelayScheduleMapper implements BasicMapper<RelaySchedule, RelayScheduleDtoIn, RelayScheduleDtoOut> {
    @Autowired
    protected RelayRepository relayRepository;
    @Autowired
    protected WeekDayMapper weekDayMapper;

    @Override
    @Mapping(target = "dayNumber", expression = "java(dtoIn.getDayNumber())")
    @Mapping(target = "relay", expression = "java(getRelay(dtoIn.getRelayId()))")
    public abstract RelaySchedule toEntity(RelayScheduleDtoIn dtoIn);

    @Override
    @Mapping(target = "relayId", source = "entity.relay.id")
    @Mapping(target = "weekDay", expression = "java(toWeekDay(entity))")
    @Mapping(target = "onStart", expression = "java(toLocalTime(entity.getOnStart()))")
    @Mapping(target = "onEnd", expression = "java(toLocalTime(entity.getOnEnd()))")
    public abstract RelayScheduleDtoOut toDtoOut(RelaySchedule entity);

    protected Relay getRelay(Long id) {
        return relayRepository.findById(id)
                .orElseThrow(() -> new ApplicationException("Cannot find relay with ID " + id, HttpStatus.NOT_FOUND));
    }

    protected WeekDay toWeekDay(RelaySchedule entity) {
        WeekDayModel.WEEK_DAY weekDay = weekDayMapper.fromPositionToWeekDayEnum(entity.getDayNumber());
        return weekDayMapper.toWeekDayFullName(weekDay);
    }

    protected LocalTime toLocalTime(String time) {
        return LocalTime.parse(time);
    }
}
