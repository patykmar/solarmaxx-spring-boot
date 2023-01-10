package cz.patyk.solarmaxx.backend.service;

import cz.patyk.solarmaxx.backend.dto.in.RelayScheduleDtoIn;
import cz.patyk.solarmaxx.backend.dto.out.RelayScheduleDtoOut;
import cz.patyk.solarmaxx.backend.entity.RelaySchedule;
import cz.patyk.solarmaxx.backend.mapper.RelayScheduleMapper;
import cz.patyk.solarmaxx.backend.repository.RelayScheduleRepository;
import org.springframework.stereotype.Service;

@Service
public class RelayScheduleService extends AbstractCrudService<RelayScheduleDtoIn, RelayScheduleDtoOut, RelaySchedule, Long> {
    public RelayScheduleService(RelayScheduleRepository repository, RelayScheduleMapper mapper, ErrorHandleService<Long> errorHandleService) {
        super(repository, mapper, errorHandleService, ServiceConstants.RELAY_SCHEDULE_NOT_FOUND_MESSAGE);
    }

}
