package cz.patyk.solarmaxx.backend.mapper.relay;

import cz.patyk.solarmaxx.DtoInConstants;
import cz.patyk.solarmaxx.EntityConstants;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.entity.Relay;
import cz.patyk.solarmaxx.backend.entity.RelayOutput;
import cz.patyk.solarmaxx.backend.service.RelayService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class RelayOutputMapperTest {
    @Mock
    RelayService relayService;
    RelayOutputMapper relayOutputMapper = Mappers.getMapper(RelayOutputMapper.class);

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(relayOutputMapper, "relayService", relayService);
    }

    @Test
    void dtoToEntity() {
        Mockito
                .when(relayService.getOneEntity(any(Long.class)))
                .thenReturn(EntityConstants.RELAY_TASMOTA_ADMIN);

        RelayOutput relayOutput = relayOutputMapper.dtoToEntity(DtoInConstants.RELAY_OUTPUT_DTO_ON);

        Assertions.assertThat(relayOutput)
                .isInstanceOf(RelayOutput.class)
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
                .when(relayService.getOneEntity(any(Long.class)))
                .thenReturn(EntityConstants.RELAY_TASMOTA_ADMIN);

        RelayOutput relayOutput = relayOutputMapper.dtoDataToEntity(DtoInConstants.RELAY_OUTPUT_DATA_DTO);


        Assertions.assertThat(relayOutput)
                .returns(DtoInConstants.RELAY_OUTPUT_DATA_DTO.getId(), RelayOutput::getId)
                .returns(DtoInConstants.RELAY_OUTPUT_DATA_DTO.getDescription(), RelayOutput::getDescription)
                .returns(DtoInConstants.RELAY_OUTPUT_DATA_DTO.getOutputId(), RelayOutput::getOutputId)
                .returns(DtoInConstants.RELAY_OUTPUT_DATA_DTO.getOutputStatus(), RelayOutput::getOutputStatus)
                .hasNoNullFieldsOrProperties();

        Assertions.assertThat(relayOutput.getRelay())
                .hasNoNullFieldsOrProperties();

    }

    @Test
    void entityToDataDto() {
    }

    @Test
    void entityToDto() {
    }
}