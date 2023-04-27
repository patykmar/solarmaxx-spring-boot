package cz.patyk.solarmaxx.backend.mapper;

import cz.patyk.solarmaxx.DtoInConstants;
import cz.patyk.solarmaxx.EntityConstants;
import cz.patyk.solarmaxx.backend.dto.RelayOutputScheduleDto;
import cz.patyk.solarmaxx.backend.dto.data.RelayOutputScheduleDataDto;
import cz.patyk.solarmaxx.backend.entity.RelayOutputSchedule;
import cz.patyk.solarmaxx.backend.model.WeekDayModel;
import cz.patyk.solarmaxx.backend.service.RelayOutputService;
import cz.patyk.solarmaxx.constants.RelayOutputEntityConstants;
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
class RelayOutputScheduleMapperTest {
    @Mock
    RelayOutputService relayOutputService;
    RelayOutputScheduleMapper relayOutputScheduleMapper = Mappers.getMapper(RelayOutputScheduleMapper.class);

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(relayOutputScheduleMapper, "weekDayMapper", new WeekDayMapper(new WeekDayModel()));
        ReflectionTestUtils.setField(relayOutputScheduleMapper, "relayOutputService", relayOutputService);
    }

    @Test
    void dtoToEntityTest() {
        Mockito.when(relayOutputService.getOneEntity(any(Long.class)))
                .thenReturn(RelayOutputEntityConstants.RELAY_OUTPUT_TASMOTA_ON_010);

        RelayOutputSchedule relayOutputSchedule = relayOutputScheduleMapper.dtoToEntity(DtoInConstants.RELAY_OUTPUT_SCHEDULE_DTO);

        Assertions.assertThat(relayOutputSchedule)
                .hasNoNullFieldsOrProperties();

        Assertions.assertThat(relayOutputSchedule.getRelayOutput())
                .hasNoNullFieldsOrPropertiesExcept("relay");
    }

    @Test
    void entityToDataDtoTest() {
        RelayOutputScheduleDataDto relayOutputScheduleDataDto =
                relayOutputScheduleMapper.entityToDataDto(EntityConstants.RELAY_OUTPUT_SCHEDULE);


        Assertions.assertThat(relayOutputScheduleDataDto)
                .hasNoNullFieldsOrProperties();
    }

    @Test
    void entityToDtoTest() {
        RelayOutputScheduleDto relayOutputScheduleDto = relayOutputScheduleMapper.entityToDto(EntityConstants.RELAY_OUTPUT_SCHEDULE);

        Assertions.assertThat(relayOutputScheduleDto)
                .hasNoNullFieldsOrProperties();
    }
}