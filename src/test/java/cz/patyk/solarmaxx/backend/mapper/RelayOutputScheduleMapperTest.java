package cz.patyk.solarmaxx.backend.mapper;

import cz.patyk.solarmaxx.DtoInConstants;
import cz.patyk.solarmaxx.EntityConstants;
import cz.patyk.solarmaxx.backend.entity.RelayOutputSchedule;
import cz.patyk.solarmaxx.backend.service.RelayOutputService;
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
        Mockito.when(relayOutputService.getOneEntity(any(Long.class)))
                .thenReturn(EntityConstants.RELAY_OUTPUT_01);

        ReflectionTestUtils.setField(relayOutputScheduleMapper, "relayOutputService", relayOutputService);
    }

    @Test
    void dtoToEntityTest() {
        RelayOutputSchedule relayOutputSchedule = relayOutputScheduleMapper.dtoToEntity(DtoInConstants.RELAY_OUTPUT_SCHEDULE_DTO);

        Assertions.assertThat(relayOutputSchedule)
                .hasNoNullFieldsOrProperties();

        Assertions.assertThat(relayOutputSchedule.getRelayOutput())
                .hasNoNullFieldsOrPropertiesExcept("relay");
    }
}