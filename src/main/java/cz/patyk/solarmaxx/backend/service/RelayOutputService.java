package cz.patyk.solarmaxx.backend.service;

import cz.patyk.solarmaxx.backend.adapter.RelayAdapter;
import cz.patyk.solarmaxx.backend.dto.data.RelayOutputDataDto;
import cz.patyk.solarmaxx.backend.dto.out.RelayDtoOut;
import cz.patyk.solarmaxx.backend.dto.relay.SupportedRelayType;
import cz.patyk.solarmaxx.backend.dto.relay.output.RelayOutputDto;
import cz.patyk.solarmaxx.backend.entity.RelayOutput;
import cz.patyk.solarmaxx.backend.exceptions.ApplicationException;
import cz.patyk.solarmaxx.backend.factory.adapter.RelayAdapterFactory;
import cz.patyk.solarmaxx.backend.mapper.relay.RelayOutputMapper;
import cz.patyk.solarmaxx.backend.repository.RelayOutputRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RelayOutputService implements CrudService<cz.patyk.solarmaxx.backend.dto.RelayOutputDto, RelayOutputDataDto, RelayOutput> {

    private final RelayService relayService;
    private final RelayAdapterFactory relayAdapterFactory;
    private final RelayOutputRepository relayOutputRepository;
    private final RelayOutputMapper relayOutputMapper;
    private final ErrorHandleService<Long> errorHandleService;

    @Deprecated
    public RelayOutputDto toggleOutput(Long relayId, Byte outputId, boolean toggle) {
        RelayDtoOut serviceOne = relayService.getOne(relayId);
        SupportedRelayType relayType = SupportedRelayType.fromString(serviceOne.getRelayTypeDtoOut().getDeviceTypeString());
        RelayAdapter relayAdapter = relayAdapterFactory.getRelayAdapter(relayType);

        RelayOutputDto relayOutputDto = serviceOne.getRelayOutputDtos().stream()
                .filter(output -> Objects.equals(output.getOutputId(), outputId))
                .findFirst()
                .orElseThrow(() -> new ApplicationException("Relay output with ID " + outputId + " not found", HttpStatus.NOT_FOUND));

        if (toggle) {
            return relayAdapter.turnOnRelayOutput(relayOutputDto, serviceOne.getIpAddress());
        } else {
            return relayAdapter.turnOffRelayOutput(relayOutputDto, serviceOne.getIpAddress());
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
    public RelayOutputDataDto newItem(cz.patyk.solarmaxx.backend.dto.RelayOutputDto dtoIn) {
        RelayOutput relayOutput = relayOutputMapper.dtoToEntity(dtoIn);
        return newItemByEntity(relayOutput);
    }

    @Override
    public RelayOutputDataDto newItemByEntity(RelayOutput newEntity) {
        return relayOutputMapper.entityToDataDto(relayOutputRepository.save(newEntity));
    }

    @Override
    public RelayOutputDataDto editItem(cz.patyk.solarmaxx.backend.dto.RelayOutputDto dtoIn, Long id) {
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
