package cz.patyk.solarmaxx.backend.service;

import cz.patyk.solarmaxx.EntityConstants;
import cz.patyk.solarmaxx.backend.dto.RelayDto;
import cz.patyk.solarmaxx.backend.dto.out.RelayDtoOut;
import cz.patyk.solarmaxx.backend.dto.out.RelayTypeDtoOut;
import cz.patyk.solarmaxx.backend.mapper.RelayScheduleMapper;
import cz.patyk.solarmaxx.backend.mapper.UserMapper;
import cz.patyk.solarmaxx.backend.mapper.WeekDayMapper;
import cz.patyk.solarmaxx.backend.mapper.relay.RelayMapper;
import cz.patyk.solarmaxx.backend.mapper.relay.RelayOutputMapper;
import cz.patyk.solarmaxx.backend.mapper.relay.type.RelayTypeMapper;
import cz.patyk.solarmaxx.backend.model.WeekDayModel;
import cz.patyk.solarmaxx.backend.repository.RelayRepository;
import cz.patyk.solarmaxx.backend.repository.UserRepository;
import org.apache.commons.lang3.math.NumberUtils;
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
class RelayServiceTest {

    RelayService relayService;
    @Mock
    RelayRepository relayRepository;

    @BeforeEach
    void setUp() {
        RelayMapper relayMapper = Mappers.getMapper(RelayMapper.class);
        ErrorHandleService<Long> errorHandleService = new ErrorHandleService<>();

        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserMapper userMapper = Mappers.getMapper(UserMapper.class);
        RelayTypeMapper relayTypeMapper = Mappers.getMapper(RelayTypeMapper.class);
        RelayScheduleMapper relayScheduleMapper = Mappers.getMapper(RelayScheduleMapper.class);
        WeekDayMapper weekDayMapper = new WeekDayMapper(new WeekDayModel());
        RelayOutputMapper relayOutputMapper = Mappers.getMapper(RelayOutputMapper.class);
        UserService userService = new UserService(userRepository, userMapper, errorHandleService);

        ReflectionTestUtils.setField(relayMapper, "userService", userService);
        ReflectionTestUtils.setField(relayMapper, "userMapper", userMapper);
        ReflectionTestUtils.setField(relayMapper, "relayTypeMapper", relayTypeMapper);
        ReflectionTestUtils.setField(relayScheduleMapper, "weekDayMapper", weekDayMapper);
        ReflectionTestUtils.setField(relayScheduleMapper, "relayRepository", relayRepository);
        ReflectionTestUtils.setField(relayMapper, "relayScheduleMapper", relayScheduleMapper);
        ReflectionTestUtils.setField(relayMapper, "relayOutputMapper", relayOutputMapper);

        relayService = new RelayService(relayRepository, relayMapper, errorHandleService);
    }

    @Test
    void getOneOnLineModeTest() {
        Mockito
                .when(relayRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(EntityConstants.RELAY_TASMOTA_ADMIN));

        RelayDtoOut oneOnLineMode = relayService.getOneOnLineMode(NumberUtils.LONG_ONE);

        Assertions.assertThat(oneOnLineMode)
                .hasNoNullFieldsOrPropertiesExcept("relaySchedulesOuts")
                .isInstanceOf(RelayDtoOut.class);

        Assertions.assertThat(oneOnLineMode.getRelayTypeDtoOut())
                .hasNoNullFieldsOrProperties()
                .isInstanceOf(RelayTypeDtoOut.class);

        Assertions.assertThat(oneOnLineMode.getRelayOutputDtos())
                .isNotNull()
                .hasSize(EntityConstants.RELAY_TASMOTA_ADMIN.getRelayOutputs().size());
    }

    @Test
    void getOneDtoTest() {
        Mockito
                .when(relayRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(EntityConstants.RELAY_TASMOTA_ADMIN));

        RelayDto oneDto = relayService.getOneDto(NumberUtils.LONG_ONE);

        Assertions.assertThat(oneDto)
                .hasNoNullFieldsOrProperties();
    }
}