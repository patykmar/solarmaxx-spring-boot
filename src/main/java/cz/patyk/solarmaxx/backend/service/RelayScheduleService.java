package cz.patyk.solarmaxx.backend.service;

import cz.patyk.solarmaxx.backend.dto.in.RelayScheduleDtoIn;
import cz.patyk.solarmaxx.backend.dto.out.RelayScheduleDtoOut;
import cz.patyk.solarmaxx.backend.entity.RelaySchedule;
import cz.patyk.solarmaxx.backend.mapper.RelayScheduleMapper;
import cz.patyk.solarmaxx.backend.repository.RelayScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class RelayScheduleService extends AbstractCrudService<RelayScheduleDtoIn, RelayScheduleDtoOut, RelaySchedule, Long> {
    private final RelayScheduleRepository relayScheduleRepository;
    private final LocalTime actualTime;

    public RelayScheduleService(RelayScheduleRepository repository, RelayScheduleMapper mapper, ErrorHandleService<Long> errorHandleService) {
        super(repository, mapper, errorHandleService, ServiceConstants.RELAY_SCHEDULE_NOT_FOUND_MESSAGE);
        relayScheduleRepository = repository;
        actualTime = LocalTime.now();
    }

    public List<RelaySchedule> getAllByDayNumberEntity(Byte dayNumber) {
        return relayScheduleRepository.findAllByDayNumber(dayNumber);
    }

    public List<RelayScheduleDtoOut> getAllByDayNumber(Byte dayNumber) {
        return getAllByDayNumberEntity(dayNumber).stream()
                .map(mapper::toDtoOut)
                .toList();
    }

    public List<RelayScheduleDtoOut> getAllRelayScheduleByDayNumberWhichShouldBeEnable(Byte dayNumber) {
        return getAllByDayNumber(dayNumber).stream()
                .filter(relaySchedule -> isTimeInRange(relaySchedule.getOnStart(), relaySchedule.getOnEnd()))
                .toList();
    }

    public List<RelayScheduleDtoOut> getAllRelayScheduleByDayNumberWhichShouldBeDisable(Byte dayNumber) {
        return getAllByDayNumber(dayNumber).stream()
                .filter(relaySchedule -> isTimeOutOfRange(relaySchedule.getOnStart(), relaySchedule.getOnEnd()))
                .toList();
    }

    private boolean isTimeInRange(LocalTime timeStart, LocalTime timeEnd) {
        boolean isAfter = actualTime.isAfter(timeStart);
        boolean isBefore = actualTime.isBefore(timeEnd);
        boolean isOneMinuteRange = timeStart.equals(timeEnd);
        boolean isInRange = isAfter && isBefore || isOneMinuteRange;
        boolean isActualTimeOnEdgeOfRange = actualTime.equals(timeStart) || actualTime.equals(timeEnd);
        return isInRange || isActualTimeOnEdgeOfRange;
    }

    private boolean isTimeOutOfRange(LocalTime timeStart, LocalTime timeEnd) {
        return !isTimeInRange(timeStart, timeEnd);
    }
}
