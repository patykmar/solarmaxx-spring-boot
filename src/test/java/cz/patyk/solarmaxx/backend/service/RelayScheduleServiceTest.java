package cz.patyk.solarmaxx.backend.service;

import cz.patyk.solarmaxx.EntityConstants;
import cz.patyk.solarmaxx.backend.dto.out.RelayScheduleDtoOut;
import cz.patyk.solarmaxx.backend.entity.RelaySchedule;
import cz.patyk.solarmaxx.backend.mapper.RelayScheduleMapper;
import cz.patyk.solarmaxx.backend.mapper.WeekDayMapper;
import cz.patyk.solarmaxx.backend.model.WeekDayModel;
import cz.patyk.solarmaxx.backend.repository.RelayRepository;
import cz.patyk.solarmaxx.backend.repository.RelayScheduleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class RelayScheduleServiceTest {
    @Mock
    RelayScheduleRepository relayScheduleRepository;
    RelayScheduleService relayScheduleService;

    byte weekDayFriday = Calendar.FRIDAY;

    @BeforeEach
    void setUp() {
        Mockito
                .when(relayScheduleRepository.findAllByDayNumber(any(Byte.class)))
                .thenReturn(provideListOfRelayScheduleForToday());
        RelayRepository relayRepository = Mockito.mock(RelayRepository.class);
        WeekDayMapper weekDayMapper = new WeekDayMapper(new WeekDayModel());

        RelayScheduleMapper relayScheduleMapper = Mappers.getMapper(RelayScheduleMapper.class);
        ErrorHandleService<Long> errorHandleService = new ErrorHandleService<>();

        ReflectionTestUtils.setField(relayScheduleMapper, "relayRepository", relayRepository);
        ReflectionTestUtils.setField(relayScheduleMapper, "weekDayMapper", weekDayMapper);

        relayScheduleService = new RelayScheduleService(relayScheduleRepository, relayScheduleMapper, errorHandleService);
    }

    @Test
    void filteredRelayScheduleByWeekDayTest() {
        List<RelayScheduleDtoOut> allRelayScheduleForToday = relayScheduleService.getAllByDayNumber(weekDayFriday);

        Assertions.assertThat(allRelayScheduleForToday)
                .isNotEmpty()
                .first()
                .returns(6L, RelayScheduleDtoOut::getId)
                .returns(EntityConstants.RELAY_TASMOTA_ADMIN.getId(), RelayScheduleDtoOut::getRelayId)
                .returns((byte) 2, RelayScheduleDtoOut::getOutputId)
                .returns(LocalTime.parse("15:11"), RelayScheduleDtoOut::getOnStart)
                .returns(LocalTime.parse("19:59"), RelayScheduleDtoOut::getOnEnd)
                .returns((byte) Calendar.FRIDAY, RelayScheduleDtoOut::getDayNumber)
        ;
    }

    private List<RelaySchedule> provideListOfRelayScheduleForToday() {
//        Calendar calendar = Calendar.getInstance();
//        int weekDayInt = calendar.get(Calendar.DAY_OF_WEEK);

        return provideListOfRelaySchedule().stream()
                .filter(relaySchedule -> relaySchedule.getDayNumber() == weekDayFriday)
                .toList();
    }

    private List<RelaySchedule> provideListOfRelaySchedule() {
        return List.of(
                RelaySchedule.builder().id(1L).relay(EntityConstants.RELAY_SHELLY_PRO_ADMIN).outputId((byte) 1).onStart("00:00").onEnd("04:59").dayNumber((byte) Calendar.SUNDAY).build(),
                RelaySchedule.builder().id(2L).relay(EntityConstants.RELAY_SHELLY_PRO_ADMIN).outputId((byte) 1).onStart("08:00").onEnd("09:14").dayNumber((byte) Calendar.TUESDAY).build(),
                RelaySchedule.builder().id(3L).relay(EntityConstants.RELAY_SHELLY_PRO_ADMIN).outputId((byte) 1).onStart("12:00").onEnd("15:10").dayNumber((byte) Calendar.THURSDAY).build(),
                RelaySchedule.builder().id(3L).relay(EntityConstants.RELAY_SHELLY_PRO_ADMIN).outputId((byte) 1).onStart("20:00").onEnd("23:59").dayNumber((byte) Calendar.SATURDAY).build(),
                RelaySchedule.builder().id(4L).relay(EntityConstants.RELAY_TASMOTA_ADMIN).outputId((byte) 2).onStart("05:00").onEnd("07:59").dayNumber((byte) Calendar.MONDAY).build(),
                RelaySchedule.builder().id(5L).relay(EntityConstants.RELAY_TASMOTA_ADMIN).outputId((byte) 2).onStart("09:15").onEnd("11:59").dayNumber((byte) Calendar.WEDNESDAY).build(),
                RelaySchedule.builder().id(6L).relay(EntityConstants.RELAY_TASMOTA_ADMIN).outputId((byte) 2).onStart("15:11").onEnd("19:59").dayNumber((byte) Calendar.FRIDAY).build()
        );
    }

    @Test
    void getAllRelayScheduleByDayNumberWhichShouldBeEnableTest() {
        Mockito
                .when(relayScheduleRepository.findAllByDayNumber(any(Byte.class)))
                .thenReturn(provideListOfRelayScheduleForFriday());

        List<RelayScheduleDtoOut> allRelayScheduleByDayNumberWhichShouldBeEnable = relayScheduleService.getAllRelayScheduleByDayNumberWhichShouldBeEnable(weekDayFriday);

        Assertions.assertThat(allRelayScheduleByDayNumberWhichShouldBeEnable)
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    void getAllRelayScheduleByDayNumberWhichShouldBeDisableTest() {
        Mockito
                .when(relayScheduleRepository.findAllByDayNumber(any(Byte.class)))
                .thenReturn(provideListOfRelayScheduleForFriday());

        List<RelayScheduleDtoOut> allRelayScheduleByDayNumberWhichShouldBeDisable = relayScheduleService.getAllRelayScheduleByDayNumberWhichShouldBeDisable(weekDayFriday);

        Assertions.assertThat(allRelayScheduleByDayNumberWhichShouldBeDisable)
                .isNotEmpty()
                .hasSize(6);
    }

    private List<RelaySchedule> provideListOfRelayScheduleForFriday() {
        return List.of(
                RelaySchedule.builder().id(1L).relay(EntityConstants.RELAY_SHELLY_PRO_ADMIN).outputId((byte) 1).onStart("00:00").onEnd("04:59").dayNumber((byte) Calendar.FRIDAY).build(),
                RelaySchedule.builder().id(2L).relay(EntityConstants.RELAY_SHELLY_PRO_ADMIN).outputId((byte) 1).onStart("08:00").onEnd("09:14").dayNumber((byte) Calendar.FRIDAY).build(),
                RelaySchedule.builder().id(3L).relay(EntityConstants.RELAY_SHELLY_PRO_ADMIN).outputId((byte) 1).onStart("12:00").onEnd("15:10").dayNumber((byte) Calendar.FRIDAY).build(),
                RelaySchedule.builder().id(3L).relay(EntityConstants.RELAY_SHELLY_PRO_ADMIN).outputId((byte) 1).onStart("20:00").onEnd("23:59").dayNumber((byte) Calendar.FRIDAY).build(),
                RelaySchedule.builder().id(4L).relay(EntityConstants.RELAY_TASMOTA_ADMIN).outputId((byte) 2).onStart("05:00").onEnd("07:59").dayNumber((byte) Calendar.FRIDAY).build(),
                RelaySchedule.builder().id(5L).relay(EntityConstants.RELAY_TASMOTA_ADMIN).outputId((byte) 2).onStart("09:15").onEnd("11:59").dayNumber((byte) Calendar.FRIDAY).build(),
                RelaySchedule.builder().id(6L).relay(EntityConstants.RELAY_TASMOTA_ADMIN).outputId((byte) 2).onStart("15:11").onEnd("19:59").dayNumber((byte) Calendar.FRIDAY).build()
        );
    }
}