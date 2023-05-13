package cz.patyk.solarmaxx.backend.service;

import cz.patyk.solarmaxx.backend.dto.RelayOutputScheduleDto;
import cz.patyk.solarmaxx.backend.dto.data.RelayOutputScheduleDataDto;
import cz.patyk.solarmaxx.backend.entity.RelayOutputScheduleEntity;
import cz.patyk.solarmaxx.backend.mapper.RelayOutputScheduleMapper;
import cz.patyk.solarmaxx.backend.repository.RelayOutputScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelayOutputScheduleService extends AbstractCrudDtoService<RelayOutputScheduleDto, RelayOutputScheduleDataDto, RelayOutputScheduleEntity, Long> {

    private final RelayOutputScheduleRepository relayOutputScheduleRepository;
    public RelayOutputScheduleService(RelayOutputScheduleRepository repository,
                                      RelayOutputScheduleMapper mapper,
                                      ErrorHandleService<Long> errorHandleService,
                                      RelayOutputScheduleRepository relayOutputScheduleRepository) {
        super(repository, mapper, errorHandleService, ServiceConstants.RELAY_SCHEDULE_NOT_FOUND_MESSAGE);
        this.relayOutputScheduleRepository = relayOutputScheduleRepository;
    }

    public List<RelayOutputScheduleDataDto> getAllByWeekDayNumber(Byte weekDayNumber){
        return relayOutputScheduleRepository.findAllByDayNumber(weekDayNumber).stream()
                .map(mapper::entityToDataDto)
                .toList();
    }
}
