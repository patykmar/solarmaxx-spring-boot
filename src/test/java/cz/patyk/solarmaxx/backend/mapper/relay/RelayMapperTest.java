package cz.patyk.solarmaxx.backend.mapper.relay;

import cz.patyk.solarmaxx.DtoInConstants;
import cz.patyk.solarmaxx.EntityConstants;
import cz.patyk.solarmaxx.ValueConstants;
import cz.patyk.solarmaxx.backend.adapter.ShellyProRelayAdapter;
import cz.patyk.solarmaxx.backend.adapter.TasmotaRelayAdapter;
import cz.patyk.solarmaxx.backend.client.ShellyProClient;
import cz.patyk.solarmaxx.backend.client.TasmotaClient;
import cz.patyk.solarmaxx.backend.config.RelayTypeConfig;
import cz.patyk.solarmaxx.backend.dto.out.RelayDtoOut;
import cz.patyk.solarmaxx.backend.dto.out.RelayScheduleDtoOut;
import cz.patyk.solarmaxx.backend.dto.out.RelayTypeDtoOut;
import cz.patyk.solarmaxx.backend.dto.out.UserDtoOut;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.dto.relay.output.RelayOutputDto;
import cz.patyk.solarmaxx.backend.dto.relay.type.RelayTypeConstants;
import cz.patyk.solarmaxx.backend.dto.relay.type.url.parameter.StatusUrlParameter;
import cz.patyk.solarmaxx.backend.dto.relay.type.url.parameter.ToggleUrlParameter;
import cz.patyk.solarmaxx.backend.entity.Relay;
import cz.patyk.solarmaxx.backend.entity.RelayType;
import cz.patyk.solarmaxx.backend.entity.User;
import cz.patyk.solarmaxx.backend.factory.mapper.RelayOutputIdFactory;
import cz.patyk.solarmaxx.backend.mapper.RelayScheduleMapper;
import cz.patyk.solarmaxx.backend.mapper.UserMapper;
import cz.patyk.solarmaxx.backend.mapper.relay.type.RelayTypeMapper;
import cz.patyk.solarmaxx.backend.mapper.relay.type.output.ShellyProOutputIdMapper;
import cz.patyk.solarmaxx.backend.mapper.relay.type.output.TasmotaOutputIdMapper;
import cz.patyk.solarmaxx.backend.mapper.relay.type.url.ShellyProUrlMapper;
import cz.patyk.solarmaxx.backend.mapper.relay.type.url.TasmotaUrlMapper;
import cz.patyk.solarmaxx.backend.mapper.relay.type.url.UrlParameterMapper;
import cz.patyk.solarmaxx.backend.service.RelayTypeService;
import cz.patyk.solarmaxx.backend.service.UserService;
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

import java.util.List;


@ExtendWith(MockitoExtension.class)
class RelayMapperTest {
    private final RelayMapper RELAY_MAPPER = Mappers.getMapper(RelayMapper.class);
    @Mock
    TasmotaClient tasmotaClient;

    @BeforeEach
    void setUp() {
        UserService userService = Mockito.mock(UserService.class);
        UserMapper userMapper = Mappers.getMapper(UserMapper.class);
        RelayTypeService relayTypeService = Mockito.mock(RelayTypeService.class);
        RelayTypeMapper relayTypeMapper = Mappers.getMapper(RelayTypeMapper.class);
        RelayScheduleMapper relayScheduleMapper = Mappers.getMapper(RelayScheduleMapper.class);

        RelayTypeConfig relayTypeConfig = new RelayTypeConfig();
        UrlParameterMapper urlParameterMapper = Mappers.getMapper(UrlParameterMapper.class);
        ShellyProUrlMapper shellyProUrlMapper = new ShellyProUrlMapper(relayTypeConfig.relayTypeUrlPattern());
        TasmotaUrlMapper tasmotaUrlMapper = new TasmotaUrlMapper(relayTypeConfig.relayTypeUrlPattern());

        TasmotaRelayAdapter tasmotaRelayAdapter = new TasmotaRelayAdapter(tasmotaClient);
        ShellyProClient shellyProClient = Mockito.mock(ShellyProClient.class);
        ShellyProRelayAdapter shellyProRelayAdapter = new ShellyProRelayAdapter(new OutputStatusMapper(), shellyProClient);

        ShellyProOutputIdMapper shellyProOutputIdMapper = new ShellyProOutputIdMapper(urlParameterMapper, shellyProUrlMapper, shellyProRelayAdapter);
        TasmotaOutputIdMapper tasmotaOutputIdMapper = new TasmotaOutputIdMapper(urlParameterMapper, tasmotaUrlMapper, tasmotaRelayAdapter);

        RelayOutputIdFactory relayOutputIdFactory = new RelayOutputIdFactory(shellyProOutputIdMapper, tasmotaOutputIdMapper);

        ReflectionTestUtils.setField(RELAY_MAPPER, "userService", userService);
        ReflectionTestUtils.setField(RELAY_MAPPER, "userMapper", userMapper);
        ReflectionTestUtils.setField(RELAY_MAPPER, "relayTypeMapper", relayTypeMapper);
        ReflectionTestUtils.setField(RELAY_MAPPER, "relayTypeService", relayTypeService);
        ReflectionTestUtils.setField(RELAY_MAPPER, "relayOutputIdFactory", relayOutputIdFactory);
        ReflectionTestUtils.setField(RELAY_MAPPER, "relayScheduleMapper", relayScheduleMapper);
    }

    @Test
    void toDtoOutTasmotaType() {
        EntityConstants.RELAY_TASMOTA_ADMIN
                .setRelaySchedules(List.of(EntityConstants.RELAY_SCHEDULE_TASMOTA));

        RelayDtoOut relayDtoOut = RELAY_MAPPER.toDtoOut(EntityConstants.RELAY_TASMOTA_ADMIN);

        Assertions.assertThat(relayDtoOut.getUser())
                .isNotNull()
                .isInstanceOf(UserDtoOut.class)
                .returns(EntityConstants.USER_ADMIN.getId(), UserDtoOut::getId)
                .returns(EntityConstants.USER_ADMIN.getEmail(), UserDtoOut::getEmail)
                .returns(EntityConstants.USER_ADMIN.getRoles(), UserDtoOut::getRoles);

        Assertions.assertThat(relayDtoOut.getRelayTypeDtoOut())
                .isNotNull()
                .isInstanceOf(RelayTypeDtoOut.class)
                .returns(EntityConstants.RELAY_TYPE_TASMOTA.getId(), RelayTypeDtoOut::getId)
                .returns(EntityConstants.RELAY_TYPE_TASMOTA.getName(), RelayTypeDtoOut::getName)
                .returns(EntityConstants.RELAY_TYPE_TASMOTA.getUrlStatus(), RelayTypeDtoOut::getUrlStatusTemplate)
                .returns(EntityConstants.RELAY_TYPE_TASMOTA.getUrlToggle(), RelayTypeDtoOut::getUrlToggleTemplate)
                .returns(EntityConstants.RELAY_TYPE_TASMOTA.getDeviceTypeString(), RelayTypeDtoOut::getDeviceTypeString);

        Assertions.assertThat(relayDtoOut)
                .returns(EntityConstants.RELAY_TASMOTA_ADMIN.getId(), RelayDtoOut::getId)
                .returns(EntityConstants.RELAY_TASMOTA_ADMIN.getName(), RelayDtoOut::getName)
                .returns(EntityConstants.RELAY_TASMOTA_ADMIN.getIpAddress(), RelayDtoOut::getIpAddress)
                .returns(EntityConstants.RELAY_TASMOTA_ADMIN.getPort(), RelayDtoOut::getPort)
                .returns(EntityConstants.RELAY_TASMOTA_ADMIN.getOutputCount(), RelayDtoOut::getOutputCount);

        Assertions.assertThat(relayDtoOut.getRelayOutputDtos())
                .isNotEmpty()
                .hasSize(EntityConstants.RELAY_TASMOTA_ADMIN.getOutputCount())
                .element(0)
                .returns(RelayTypeConstants.TASMOTA_DEFAULT_OUTPUT_ID, RelayOutputDto::getOutputId)
                .returns(OutputStatus.NA, RelayOutputDto::getOutputStatus)
                .returns("http://1.2.3.4:80/cm?cmnd=Power1%20STATUS", RelayOutputDto::getStatusUrl)
                .returns("http://1.2.3.4:80/cm?cmnd=Power1%20ON", RelayOutputDto::getTurnOnUrl)
                .returns("http://1.2.3.4:80/cm?cmnd=Power1%20OFF", RelayOutputDto::getTurnOffUrl);

        Assertions.assertThat(relayDtoOut.getRelaySchedulesOuts())
                .isNotEmpty()
                .hasSize(1)
                .first()
                .returns(NumberUtils.LONG_ONE, RelayScheduleDtoOut::getId)
                .returns(EntityConstants.RELAY_TASMOTA_ADMIN.getId(), RelayScheduleDtoOut::getRelayId)
                .returns(ValueConstants.RELAY_SCHEDULE_ON_START, RelayScheduleDtoOut::getOnStart)
                .returns(ValueConstants.RELAY_SCHEDULE_ON_END, RelayScheduleDtoOut::getOnEnd)
                .returns(NumberUtils.BYTE_ONE, RelayScheduleDtoOut::getDayNumber);
    }

    @Test
    void toDtoOutShellyProType() {
        RelayDtoOut relayDtoOut = RELAY_MAPPER.toDtoOut(EntityConstants.RELAY_SHELLY_PRO_ADMIN);

        Assertions.assertThat(relayDtoOut.getUser())
                .isNotNull()
                .isInstanceOf(UserDtoOut.class)
                .returns(EntityConstants.USER_ADMIN.getId(), UserDtoOut::getId)
                .returns(EntityConstants.USER_ADMIN.getEmail(), UserDtoOut::getEmail)
                .returns(EntityConstants.USER_ADMIN.getRoles(), UserDtoOut::getRoles);

        Assertions.assertThat(relayDtoOut.getRelayTypeDtoOut())
                .isNotNull()
                .isInstanceOf(RelayTypeDtoOut.class)
                .returns(EntityConstants.RELAY_TYPE_SHELLY.getId(), RelayTypeDtoOut::getId)
                .returns(EntityConstants.RELAY_TYPE_SHELLY.getName(), RelayTypeDtoOut::getName)
                .returns(EntityConstants.RELAY_TYPE_SHELLY.getUrlStatus(), RelayTypeDtoOut::getUrlStatusTemplate)
                .returns(EntityConstants.RELAY_TYPE_SHELLY.getUrlToggle(), RelayTypeDtoOut::getUrlToggleTemplate)
                .returns(EntityConstants.RELAY_TYPE_SHELLY.getDeviceTypeString(), RelayTypeDtoOut::getDeviceTypeString);

        Assertions.assertThat(relayDtoOut)
                .returns(EntityConstants.RELAY_SHELLY_PRO_ADMIN.getId(), RelayDtoOut::getId)
                .returns(EntityConstants.RELAY_SHELLY_PRO_ADMIN.getName(), RelayDtoOut::getName)
                .returns(EntityConstants.RELAY_SHELLY_PRO_ADMIN.getIpAddress(), RelayDtoOut::getIpAddress)
                .returns(EntityConstants.RELAY_SHELLY_PRO_ADMIN.getPort(), RelayDtoOut::getPort)
                .returns(EntityConstants.RELAY_SHELLY_PRO_ADMIN.getOutputCount(), RelayDtoOut::getOutputCount);

        Assertions.assertThat(relayDtoOut.getRelayOutputDtos())
                .isNotEmpty()
                .hasSize(EntityConstants.RELAY_SHELLY_PRO_ADMIN.getOutputCount())
                .element(0)
                .returns(RelayTypeConstants.SHALLY_PRO_DEFAULT_OUTPUT_ID, RelayOutputDto::getOutputId)
                .returns(OutputStatus.NA, RelayOutputDto::getOutputStatus)
                .returns("http://1.2.3.4:80/rpc/Switch.GetStatus?id=0", RelayOutputDto::getStatusUrl)
                .returns("http://1.2.3.4:80/rpc/Switch.Toggle?id=0", RelayOutputDto::getTurnOnUrl)
                .returns("http://1.2.3.4:80/rpc/Switch.Toggle?id=0", RelayOutputDto::getTurnOffUrl);
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