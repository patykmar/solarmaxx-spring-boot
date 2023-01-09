package cz.patyk.solarmaxx.backend.mapper;

import cz.patyk.solarmaxx.DtoInConstants;
import cz.patyk.solarmaxx.EntityConstants;
import cz.patyk.solarmaxx.backend.dto.out.RelayScheduleDtoOut;
import cz.patyk.solarmaxx.backend.entity.RelaySchedule;
import cz.patyk.solarmaxx.backend.mapper.relay.RelayMapper;
import cz.patyk.solarmaxx.backend.service.RelayService;
import org.apache.commons.lang3.math.NumberUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

class RelayScheduleMapperTest {
    RelayScheduleMapper relayScheduleMapper = Mappers.getMapper(RelayScheduleMapper.class);
    static final String ON_START = "12:00";
    static final String ON_END = "16:00";

    @BeforeEach
    void setUp() {
        RelayService relayService = Mockito.mock(RelayService.class);
        RelayMapper relayMapper = Mockito.mock(RelayMapper.class);

        Mockito.when(relayService.getOneEntity(NumberUtils.LONG_ONE))
                .thenReturn(EntityConstants.RELAY_TASMOTA_ADMIN);

        ReflectionTestUtils.setField(relayScheduleMapper, "relayMapper", relayMapper);
        ReflectionTestUtils.setField(relayScheduleMapper, "relayService", relayService);
    }

    @Test
    void toEntity() {
        RelaySchedule relaySchedule = relayScheduleMapper.toEntity(DtoInConstants.RELAY_SCHEDULE_DTO_IN);

        Assertions.assertThat(relaySchedule)
                .returns((byte) 1, RelaySchedule::getDayNumber)
                .returns(ON_START, RelaySchedule::getOnStart)
                .returns(ON_END, RelaySchedule::getOnEnd);
    }

    @Test
    void toDtoOut() {

        RelayScheduleDtoOut relayScheduleDtoOut = relayScheduleMapper.toDtoOut(EntityConstants.RELAY_SCHEDULE_TASMOTA);

        Assertions.assertThat(relayScheduleDtoOut)
                .returns(ON_START, RelayScheduleDtoOut::getOnStart)
                .returns(ON_END, RelayScheduleDtoOut::getOnEnd)
        ;
    }

}