package cz.patyk.solarmaxx.backend.service;

import cz.patyk.solarmaxx.backend.adapter.RelayAdapter;
import cz.patyk.solarmaxx.backend.dto.RelayOutputDto;
import cz.patyk.solarmaxx.backend.dto.data.RelayOutputDataDto;
import cz.patyk.solarmaxx.backend.dto.relay.SupportedRelayType;
import cz.patyk.solarmaxx.backend.entity.RelayOutput;
import cz.patyk.solarmaxx.backend.factory.adapter.RelayAdapterFactory;
import cz.patyk.solarmaxx.backend.mapper.relay.RelayOutputMapper;
import cz.patyk.solarmaxx.backend.repository.RelayOutputRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RelayOutputService implements CrudService<RelayOutputDto, RelayOutputDataDto, RelayOutput> {

    private final RelayAdapterFactory relayAdapterFactory;
    private final RelayOutputRepository relayOutputRepository;
    private final RelayOutputMapper relayOutputMapper;
    private final ErrorHandleService<Long> errorHandleService;

    public void toggleOutput(Long relayOutputId, boolean toggle) {
        RelayOutput relayOutput = getOneEntity(relayOutputId);
        RelayOutputDataDto relayOutputDataDto = relayOutputMapper.entityToDataDto(relayOutput);
        String relayTypeAsString = relayOutput.getRelay().getRelayType().getDeviceTypeString();
        RelayAdapter relayAdapter = relayAdapterFactory.getRelayAdapter(SupportedRelayType.fromString(relayTypeAsString));

        if (toggle) {
            relayAdapter.turnOnRelayOutput(relayOutputDataDto);
        } else {
            relayAdapter.turnOffRelayOutput(relayOutputDataDto);
        }
    }

    @Override
    public List<RelayOutputDataDto> getAll(Pageable pageable) {
        return relayOutputRepository.findAll(pageable)
                .map(relayOutputMapper::entityToDataDto)
                .toList();
    }

    @Override
    public List<RelayOutputDataDto> getAll() {
        return relayOutputRepository.findAll().stream()
                .map(relayOutputMapper::entityToDataDto)
                .toList();
    }

    public List<RelayOutputDataDto> getAllByRelayId(Long relayId) {
        return relayOutputRepository.findAllByRelayId(relayId).stream()
                .map(relayOutputMapper::entityToDataDto)
                .toList();
    }

    public RelayOutput toEntity(RelayOutputDataDto relayOutputDataDto) {
        return relayOutputMapper.dtoDataToEntity(relayOutputDataDto);
    }

    public RelayOutput toEntityFromDevice(RelayOutputDataDto relayOutputDataDto) {
        return relayOutputMapper.fromDeviceRelayOutputDataDtoToEntity(relayOutputDataDto);
    }

    @Override
    public RelayOutputDataDto getOne(Long id) {
        return relayOutputMapper.entityToDataDto(getOneEntity(id));
    }

    @Override
    public RelayOutput getOneEntity(Long id) {
        return relayOutputRepository.findById(id)
                .orElseThrow(() -> errorHandleService.handleNotFoundError(id, ServiceConstants.RELAY_OUTPUT_NOT_FOUND_MESSAGE));
    }

    @Override
    public RelayOutputDataDto newItem(RelayOutputDto dtoIn) {
        RelayOutput relayOutput = relayOutputMapper.dtoToEntity(dtoIn);
        return newItemByEntity(relayOutput);
    }

    @Override
    public RelayOutputDataDto newItemByEntity(RelayOutput newEntity) {
        return relayOutputMapper.entityToDataDto(relayOutputRepository.save(newEntity));
    }

    @Override
    public RelayOutputDataDto editItem(RelayOutputDto dtoIn, Long id) {
        RelayOutput relayOutput = relayOutputMapper.dtoToEntity(dtoIn);
        relayOutput.setId(id);
        return editItemByEntity(relayOutput);
    }

    @Override
    public RelayOutputDataDto editItemByEntity(RelayOutput entity) {
        checkIfExistEntity(entity.getId());
        return relayOutputMapper.entityToDataDto(relayOutputRepository.save(entity));
    }

    @Override
    public void deleteItem(Long id) {
        checkIfExistEntity(id);
        relayOutputRepository.deleteById(id);
    }

    protected void checkIfExistEntity(Long id) {
        if (!relayOutputRepository.existsById(id)) {
            throw errorHandleService.handleNotFoundError(id, ServiceConstants.RELAY_OUTPUT_NOT_FOUND_MESSAGE);
        }
    }
}
