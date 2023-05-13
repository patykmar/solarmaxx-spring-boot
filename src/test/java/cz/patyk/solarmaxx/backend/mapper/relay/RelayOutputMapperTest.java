package cz.patyk.solarmaxx.backend.mapper.relay;

import cz.patyk.solarmaxx.DtoDataConstants;
import cz.patyk.solarmaxx.DtoInConstants;
import cz.patyk.solarmaxx.EntityConstants;
import cz.patyk.solarmaxx.backend.dto.RelayOutputDto;
import cz.patyk.solarmaxx.backend.dto.data.RelayOutputDataDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.entity.Relay;
import cz.patyk.solarmaxx.backend.entity.RelayOutput;
import cz.patyk.solarmaxx.backend.repository.RelayRepository;
import cz.patyk.solarmaxx.constants.RelayEntityConstants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class RelayOutputMapperTest {
    @Mock
    RelayRepository relayRepository;
    RelayOutputMapper relayOutputMapper = Mappers.getMapper(RelayOutputMapper.class);

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(relayOutputMapper, "relayRepository", relayRepository);
    }

    @Test
    void dtoToEntity() {
        Mockito
                .when(relayRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(RelayEntityConstants.RELAY_TASMOTA_ADMIN_ON));

        RelayOutput relayOutput = relayOutputMapper.dtoToEntity(DtoInConstants.RELAY_OUTPUT_DTO_ON);

        Assertions.assertThat(relayOutput)
                .isInstanceOf(RelayOutput.class)
                .hasNoNullFieldsOrProperties()
                .returns(DtoInConstants.RELAY_OUTPUT_DTO_ON.getId(), RelayOutput::getId)
                .returns(DtoInConstants.RELAY_OUTPUT_DTO_ON.getDescription(), RelayOutput::getDescription)
                .returns(DtoInConstants.RELAY_OUTPUT_DTO_ON.getOutputId(), RelayOutput::getOutputId)
                .returns(OutputStatus.ON, RelayOutput::getOutputStatus);

        Assertions.assertThat(relayOutput.getRelay())
                .isInstanceOf(Relay.class)
                .hasNoNullFieldsOrProperties();
    }

    @Test
    void dtoDataToEntity() {
        Mockito
                .when(relayRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(RelayEntityConstants.RELAY_TASMOTA_ADMIN_ON));

        RelayOutput relayOutput = relayOutputMapper.dtoDataToEntity(DtoDataConstants.RELAY_OUTPUT_DATA_DTO_01);

        Assertions.assertThat(relayOutput)
                .returns(DtoDataConstants.RELAY_OUTPUT_DATA_DTO_01.getId(), RelayOutput::getId)
                .returns(DtoDataConstants.RELAY_OUTPUT_DATA_DTO_01.getDescription(), RelayOutput::getDescription)
                .returns(DtoDataConstants.RELAY_OUTPUT_DATA_DTO_01.getOutputId(), RelayOutput::getOutputId)
                .returns(DtoDataConstants.RELAY_OUTPUT_DATA_DTO_01.getOutputStatus(), RelayOutput::getOutputStatus)
                .hasNoNullFieldsOrProperties();

        Assertions.assertThat(relayOutput.getRelay())
                .hasNoNullFieldsOrProperties();
    }

    @Test
    void entityToDataDto() {
        RelayOutputDataDto relayOutputDataDto = relayOutputMapper.entityToDataDto(EntityConstants.RELAY_OUTPUT_STATUS_ON);

        Assertions.assertThat(relayOutputDataDto)
                .hasNoNullFieldsOrPropertiesExcept("deviceOutputStatus")
                .returns(EntityConstants.RELAY_OUTPUT_STATUS_ON.getId(), RelayOutputDataDto::getId)
                .returns(EntityConstants.RELAY_OUTPUT_STATUS_ON.getDescription(), RelayOutputDataDto::getDescription)
                .returns(EntityConstants.RELAY_OUTPUT_STATUS_ON.getOutputId(), RelayOutputDataDto::getOutputId)
                .returns(EntityConstants.RELAY_OUTPUT_STATUS_ON.getOutputStatus(), RelayOutputDataDto::getOutputStatus);
    }

    @Test
    void entityToDto() {
        RelayOutputDto relayOutputDto = relayOutputMapper.entityToDto(EntityConstants.RELAY_OUTPUT_STATUS_ON);

        Assertions.assertThat(relayOutputDto)
                .returns(EntityConstants.RELAY_OUTPUT_STATUS_ON.getId(), RelayOutputDto::getId)
                .returns(EntityConstants.RELAY_OUTPUT_STATUS_ON.getDescription(), RelayOutputDto::getDescription)
                .returns(EntityConstants.RELAY_OUTPUT_STATUS_ON.getOutputId(), RelayOutputDto::getOutputId)
                .returns(EntityConstants.RELAY_OUTPUT_STATUS_ON.getOutputStatus().toString(), RelayOutputDto::getOutputStatus)
                .returns(EntityConstants.RELAY_OUTPUT_STATUS_ON.getRelay().getId(), RelayOutputDto::getRelayId);
    }
}