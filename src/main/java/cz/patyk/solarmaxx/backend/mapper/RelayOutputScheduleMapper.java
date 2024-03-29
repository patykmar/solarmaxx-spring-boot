package cz.patyk.solarmaxx.backend.mapper;

import cz.patyk.solarmaxx.backend.dto.RelayOutputScheduleDto;
import cz.patyk.solarmaxx.backend.dto.data.RelayOutputScheduleDataDto;
import cz.patyk.solarmaxx.backend.entity.RelayOutput;
import cz.patyk.solarmaxx.backend.entity.RelayOutputSchedule;
import cz.patyk.solarmaxx.backend.model.WeekDayModel;
import cz.patyk.solarmaxx.backend.service.RelayOutputService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;

@Mapper(componentModel = "spring")
public abstract class RelayOutputScheduleMapper implements BasicDataMapper<RelayOutputSchedule, RelayOutputScheduleDto, RelayOutputScheduleDataDto> {
    @Autowired
    protected RelayOutputService relayOutputService;
    @Autowired
    protected WeekDayMapper weekDayMapper;

    @Override
    @Mapping(target = "relayOutput", expression = "java(getRelayOutputById(dto.getRelayOutputId()))")
    public abstract RelayOutputSchedule dtoToEntity(RelayOutputScheduleDto dto);

    @Override
    @Mapping(target = "relayOutput", expression = "java(getRelayOutputById(dataDto.getRelayOutputId()))")
    @Mapping(target = "dayNumber", expression = "java(getPositionFromWeekDay(dataDto.getWeekDay()))")
    public abstract RelayOutputSchedule dtoDataToEntity(RelayOutputScheduleDataDto dataDto);

    @Override
    @Mapping(target = "onStart", expression = "java(toLocalTime(entity.getOnStart()))")
    @Mapping(target = "onEnd", expression = "java(toLocalTime(entity.getOnEnd()))")
    @Mapping(target = "weekDay", expression = "java(toWeekDay(entity.getDayNumber()))")
    @Mapping(target = "relayOutputId", source = "entity.relayOutput.outputId")
    @Mapping(target = "relayOutputDescription", source = "entity.relayOutput.description")
    @Mapping(target = "relayOutputStatus", source = "entity.relayOutput.outputStatus")
    @Mapping(target = "relayId", source = "entity.relayOutput.relay.id")
    public abstract RelayOutputScheduleDataDto entityToDataDto(RelayOutputSchedule entity);

    @Override
    @Mapping(target = "relayOutputId", source = "relayOutput.id")
    public abstract RelayOutputScheduleDto entityToDto(RelayOutputSchedule entity);

    protected RelayOutput getRelayOutputById(Long id) {
        return relayOutputService.getOneEntity(id);
    }

    protected WeekDayModel.WEEK_DAY toWeekDay(Byte dayNumber) {
        return weekDayMapper.fromPositionToWeekDayEnum(dayNumber);
    }

    protected Byte getPositionFromWeekDay(WeekDayModel.WEEK_DAY weekDay) {
        return weekDayMapper.toBytePositionFromWeekDayEnum(weekDay);
    }

    protected LocalTime toLocalTime(String time) {
        return LocalTime.parse(time);
    }

}
