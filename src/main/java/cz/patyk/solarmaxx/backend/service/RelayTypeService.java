package cz.patyk.solarmaxx.backend.service;

import cz.patyk.solarmaxx.backend.dto.in.RelayTypeDtoIn;
import cz.patyk.solarmaxx.backend.dto.out.RelayTypeDtoOut;
import cz.patyk.solarmaxx.backend.entity.RelayType;
import cz.patyk.solarmaxx.backend.mapper.relay.type.RelayTypeMapper;
import cz.patyk.solarmaxx.backend.repository.RelayTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class RelayTypeService extends AbstractCrudService<RelayTypeDtoIn, RelayTypeDtoOut, RelayType, Long> {

    public RelayTypeService(RelayTypeRepository repository, RelayTypeMapper mapper, ErrorHandleService<Long> errorHandleService) {
        super(repository, mapper, errorHandleService, ServiceConstants.RELAY_TYPE_NOT_FOUND_MESSAGE);
    }

}