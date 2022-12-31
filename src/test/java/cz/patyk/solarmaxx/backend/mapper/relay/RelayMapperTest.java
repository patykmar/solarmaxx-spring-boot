package cz.patyk.solarmaxx.backend.mapper.relay;

import cz.patyk.solarmaxx.DtoInConstants;
import cz.patyk.solarmaxx.EntityConstants;
import cz.patyk.solarmaxx.backend.dto.out.RelayDtoOut;
import cz.patyk.solarmaxx.backend.dto.relay.type.url.parameter.StatusUrlParameter;
import cz.patyk.solarmaxx.backend.dto.relay.type.url.parameter.ToggleUrlParameter;
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
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

class RelayMapperTest {
    private final RelayMapper RELAY_MAPPER = Mappers.getMapper(RelayMapper.class);

    @BeforeEach
    void setUp() {
        UserService userService = Mockito.mock(UserService.class);
        UserMapper userMapper = Mappers.getMapper(UserMapper.class);
        RelayTypeService relayTypeService = Mockito.mock(RelayTypeService.class);
        RelayTypeMapper relayTypeMapper = Mappers.getMapper(RelayTypeMapper.class);
//        CountryService countryService = Mockito.mock(CountryService.class);
        // inject countryMapper for testing purpose, because autowire is not working
        ReflectionTestUtils.setField(RELAY_MAPPER, "userService", userService);
        ReflectionTestUtils.setField(RELAY_MAPPER, "userMapper", userMapper);
        ReflectionTestUtils.setField(RELAY_MAPPER, "relayTypeMapper", relayTypeMapper);
        ReflectionTestUtils.setField(RELAY_MAPPER, "relayTypeService", relayTypeService);
    }


    @Test
    void toDtoOut() {
        RelayDtoOut relayDtoOut = RELAY_MAPPER.toDtoOut(EntityConstants.RELAY_TASMOTA_ADMIN);

        Assertions.assertThat(relayDtoOut)
                .returns(EntityConstants.RELAY_TASMOTA_ADMIN.getId(), RelayDtoOut::getId)
                .returns(EntityConstants.RELAY_TASMOTA_ADMIN.getName(), RelayDtoOut::getName)
                .returns(EntityConstants.RELAY_TASMOTA_ADMIN.getIpAddress(), RelayDtoOut::getIpAddress)
                .returns(EntityConstants.RELAY_TASMOTA_ADMIN.getPort(), RelayDtoOut::getPort)
                .returns(EntityConstants.RELAY_TASMOTA_ADMIN.getOutputCount(), RelayDtoOut::getOutputCount)
        ;
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
        //TODO added Mockito for User and DeviceType

        Assertions.assertThat(relay)
                .returns(DtoInConstants.RELAY_DTO_IN.getIpAddress(), Relay::getIpAddress)
                .returns(DtoInConstants.RELAY_DTO_IN.getName(), Relay::getName)
                .returns(DtoInConstants.RELAY_DTO_IN.getPort(), Relay::getPort)
                .returns(DtoInConstants.RELAY_DTO_IN.getOutputCount(), Relay::getOutputCount);

        Assertions.assertThat(relay.getUser())
                .returns(EntityConstants.USER_ADMIN.getId(), User::getId)
                .returns(EntityConstants.USER_ADMIN.getEmail(), User::getEmail)
                .returns(EntityConstants.USER_ADMIN.getRoles(), User::getRoles)
                .returns(EntityConstants.USER_ADMIN.getPassword(), User::getPassword);

        Assertions.assertThat(relay.getRelayType())
                .returns(EntityConstants.RELAY_TYPE_TASMOTA.getId(), RelayType::getId)
                .returns(EntityConstants.RELAY_TYPE_TASMOTA.getDeviceTypeString(), RelayType::getDeviceTypeString)
                .returns(EntityConstants.RELAY_TYPE_TASMOTA.getUrlToggle(), RelayType::getUrlToggle)
                .returns(EntityConstants.RELAY_TYPE_TASMOTA.getUrlStatus(), RelayType::getUrlStatus)
                .returns(EntityConstants.RELAY_TYPE_TASMOTA.getName(), RelayType::getName)
                .returns(EntityConstants.RELAY_TYPE_TASMOTA.getTurnOn(), RelayType::getTurnOn)
                .returns(EntityConstants.RELAY_TYPE_TASMOTA.getTurnOff(), RelayType::getTurnOff);
    }

    @Test
    void toStatusUrlParameterTest() {
        Assertions.assertThat(RELAY_MAPPER.toStatusUrlParameter(EntityConstants.RELAY_TASMOTA_ADMIN))
                .returns(EntityConstants.RELAY_TASMOTA_ADMIN.getRelayType().getUrlStatus(), StatusUrlParameter::getUrlTemplate)
                .returns(EntityConstants.RELAY_TASMOTA_ADMIN.getIpAddress(), StatusUrlParameter::getIpAddress)
                .returns(EntityConstants.RELAY_TASMOTA_ADMIN.getPort(), StatusUrlParameter::getPort)
                .returns(null, StatusUrlParameter::getOutputId);
    }

    @Test
    void toToggleUrlParameterTest() {
        Assertions.assertThat(RELAY_MAPPER.toToggleUrlParameter(EntityConstants.RELAY_TASMOTA_ADMIN))
                .returns(EntityConstants.RELAY_TASMOTA_ADMIN.getRelayType().getUrlToggle(), ToggleUrlParameter::getUrlTemplate)
                .returns(EntityConstants.RELAY_TASMOTA_ADMIN.getIpAddress(), ToggleUrlParameter::getIpAddress)
                .returns(EntityConstants.RELAY_TASMOTA_ADMIN.getPort(), ToggleUrlParameter::getPort)
                .returns(null, ToggleUrlParameter::getOutputId);
    }

}