package cz.patyk.solarmaxx.backend.service;

import cz.patyk.solarmaxx.backend.dto.in.RelayTypeDtoIn;
import cz.patyk.solarmaxx.backend.dto.out.RelayTypeDtoOut;
import cz.patyk.solarmaxx.backend.entity.RelayType;
import cz.patyk.solarmaxx.backend.mapper.relay.type.RelayTypeMapper;
import cz.patyk.solarmaxx.backend.repository.RelayTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RelayTypeService implements CrudService<RelayTypeDtoIn, RelayTypeDtoOut, RelayType> {
    private final RelayTypeRepository repository;
    private final RelayTypeMapper mapper;
    private final ErrorHandleService errorHandleService;
    @Override
    public List<RelayTypeDtoOut> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toDtoOut)
                .toList();
    }

    @Override
    public RelayTypeDtoOut getOne(Long id) {
        return mapper.toDtoOut(getOneEntity(id));
    }

    @Override
    public RelayType getOneEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> errorHandleService.handleNotFoundError(id, ServiceConstants.RELAY_TYPE_NOT_FOUND_MESSAGE));
    }

    @Override
    public RelayTypeDtoOut newItem(RelayTypeDtoIn dtoIn) {
        RelayType relayType = mapper.toEntity(dtoIn);
        return mapper.toDtoOut(repository.save(relayType));
    }

    @Override
    public RelayTypeDtoOut editItem(RelayTypeDtoIn dtoIn, Long id) {
        checkIfExistEntity(id);
        RelayType relayType = mapper.toEntity(dtoIn);
        relayType.setId(id);
        return mapper.toDtoOut(repository.save(relayType));
    }

    @Override
    public void deleteItem(Long id) {
        checkIfExistEntity(id);
        repository.deleteById(id);
    }

    protected void checkIfExistEntity(Long id) {
        if (!repository.existsById(id)) {
            throw errorHandleService.handleNotFoundError(id, ServiceConstants.RELAY_TYPE_NOT_FOUND_MESSAGE);
        }
    }
}
