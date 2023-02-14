package cz.patyk.solarmaxx.backend.service;

import cz.patyk.solarmaxx.backend.dto.RelayDto;
import cz.patyk.solarmaxx.backend.dto.in.RelayDtoIn;
import cz.patyk.solarmaxx.backend.dto.out.RelayDtoOut;
import cz.patyk.solarmaxx.backend.entity.Relay;
import cz.patyk.solarmaxx.backend.mapper.relay.RelayMapper;
import cz.patyk.solarmaxx.backend.repository.RelayRepository;
import org.springframework.stereotype.Service;

@Service
public class RelayService extends AbstractCrudService<RelayDtoIn, RelayDtoOut, Relay, Long> {
    private final RelayMapper relayMapper;

    public RelayService(
            RelayRepository repository,
            RelayMapper mapper,
            ErrorHandleService<Long> errorHandleService
    ) {
        super(repository, mapper, errorHandleService, ServiceConstants.RELAY_NOT_FOUND_MESSAGE);
        this.relayMapper = mapper;
    }

    public RelayDtoOut getOneOnLineMode(Long id) {
        return relayMapper.toDtoOutOnLineMode(getOneEntity(id));
    }

    public RelayDto getOneDto(Long id) {
        return relayMapper.entityToDto(getOneEntity(id));
    }

}
