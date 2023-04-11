package cz.patyk.solarmaxx.backend.mapper.relay;

import cz.patyk.solarmaxx.DtoInConstants;
import cz.patyk.solarmaxx.EntityConstants;
import cz.patyk.solarmaxx.backend.dto.out.RelayDtoOut;
import cz.patyk.solarmaxx.backend.dto.out.RelayTypeDtoOut;
import cz.patyk.solarmaxx.backend.dto.out.UserDtoOut;
import cz.patyk.solarmaxx.backend.entity.Relay;
import cz.patyk.solarmaxx.backend.entity.RelayType;
import cz.patyk.solarmaxx.backend.entity.User;
import cz.patyk.solarmaxx.backend.mapper.UserMapper;
import cz.patyk.solarmaxx.backend.mapper.relay.type.RelayTypeMapper;
import cz.patyk.solarmaxx.backend.service.RelayTypeService;
import cz.patyk.solarmaxx.backend.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;


@ExtendWith(MockitoExtension.class)
class RelayMapperTest {
    private final RelayMapper RELAY_MAPPER = Mappers.getMapper(RelayMapper.class);

    @BeforeEach
    void setUp() {
        UserService userService = Mockito.mock(UserService.class);
        UserMapper userMapper = Mappers.getMapper(UserMapper.class);
        RelayTypeService relayTypeService = Mockito.mock(RelayTypeService.class);
        RelayTypeMapper relayTypeMapper = Mappers.getMapper(RelayTypeMapper.class);
        RelayOutputMapper relayOutputMapper = Mappers.getMapper(RelayOutputMapper.class);


        ReflectionTestUtils.setField(RELAY_MAPPER, "userService", userService);
        ReflectionTestUtils.setField(RELAY_MAPPER, "userMapper", userMapper);
        ReflectionTestUtils.setField(RELAY_MAPPER, "relayTypeMapper", relayTypeMapper);
        ReflectionTestUtils.setField(RELAY_MAPPER, "relayTypeService", relayTypeService);
        ReflectionTestUtils.setField(RELAY_MAPPER, "relayOutputMapper", relayOutputMapper);
    }

    @Test
    void toDtoOutTasmotaType() {
        RelayDtoOut relayDtoOut = RELAY_MAPPER.toDtoOut(EntityConstants.RELAY_TASMOTA_ADMIN);

        Assertions.assertThat(relayDtoOut)
                .hasNoNullFieldsOrPropertiesExcept("relayOutputDtos", "relaySchedulesOuts")
                .returns(EntityConstants.RELAY_TASMOTA_ADMIN.getId(), RelayDtoOut::getId)
                .returns(EntityConstants.RELAY_TASMOTA_ADMIN.getName(), RelayDtoOut::getName)
                .returns(EntityConstants.RELAY_TASMOTA_ADMIN.getIpAddress(), RelayDtoOut::getIpAddress)
                .returns(EntityConstants.RELAY_TASMOTA_ADMIN.getPort(), RelayDtoOut::getPort)
                .returns(EntityConstants.RELAY_TASMOTA_ADMIN.getOutputCount(), RelayDtoOut::getOutputCount);

        Assertions.assertThat(relayDtoOut.getUser())
                .hasNoNullFieldsOrProperties()
                .isInstanceOf(UserDtoOut.class);

        Assertions.assertThat(relayDtoOut.getRelayTypeDtoOut())
                .hasNoNullFieldsOrProperties()
                .isInstanceOf(RelayTypeDtoOut.class);
    }

    @Test
    void toDtoOutShellyProType() {
        RelayDtoOut relayDtoOut = RELAY_MAPPER.toDtoOut(EntityConstants.RELAY_SHELLY_PRO_ADMIN);

        Assertions.assertThat(relayDtoOut)
                .returns(EntityConstants.RELAY_SHELLY_PRO_ADMIN.getId(), RelayDtoOut::getId)
                .returns(EntityConstants.RELAY_SHELLY_PRO_ADMIN.getName(), RelayDtoOut::getName)
                .returns(EntityConstants.RELAY_SHELLY_PRO_ADMIN.getIpAddress(), RelayDtoOut::getIpAddress)
                .returns(EntityConstants.RELAY_SHELLY_PRO_ADMIN.getPort(), RelayDtoOut::getPort)
                .returns(EntityConstants.RELAY_SHELLY_PRO_ADMIN.getOutputCount(), RelayDtoOut::getOutputCount);

        Assertions.assertThat(relayDtoOut.getUser())
                .hasNoNullFieldsOrProperties()
                .isInstanceOf(UserDtoOut.class);

        Assertions.assertThat(relayDtoOut.getRelayTypeDtoOut())
                .isNotNull()
                .isInstanceOf(RelayTypeDtoOut.class);
    }

    @Test
    void toEntityTest() {
        Mockito
                .when(RELAY_MAPPER.userService.getOneEntity(DtoInConstants.RELAY_DTO_IN.getUserId()))
                .thenReturn(EntityConstants.USER_ADMIN);
        Mockito
                .when(RELAY_MAPPER.relayTypeService.getOneEntity(DtoInConstants.RELAY_DTO_IN.getRelayTypeDtoId()))
                .thenReturn(EntityConstants.RELAY_TYPE_TASMOTA);

        Relay relay = RELAY_MAPPER.toEntity(DtoInConstants.RELAY_DTO_IN);

        Assertions.assertThat(relay)
                .returns(DtoInConstants.RELAY_DTO_IN.getIpAddress(), Relay::getIpAddress)
                .returns(DtoInConstants.RELAY_DTO_IN.getName(), Relay::getName)
                .returns(DtoInConstants.RELAY_DTO_IN.getPort(), Relay::getPort)
                .returns(DtoInConstants.RELAY_DTO_IN.getOutputCount(), Relay::getOutputCount);

        Assertions.assertThat(relay.getUser())
                .hasNoNullFieldsOrProperties()
                .isInstanceOf(User.class);

        Assertions.assertThat(relay.getRelayType())
                .hasNoNullFieldsOrPropertiesExcept("relays")
                .isInstanceOf(RelayType.class);
    }
}