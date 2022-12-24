package cz.patyk.solarmaxx.backend.mapper.relay.type;

import cz.patyk.solarmaxx.backend.dto.in.RelayTypeDtoIn;
import cz.patyk.solarmaxx.backend.dto.out.RelayTypeDtoOut;
import cz.patyk.solarmaxx.backend.dto.relay.RelayConstants;
import cz.patyk.solarmaxx.backend.entity.RelayType;
import org.apache.commons.lang3.math.NumberUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;

import java.util.stream.Stream;

class RelayTypeMapperTest {
    private final RelayTypeMapper relayTypeMapper = Mappers.getMapper(RelayTypeMapper.class);
    private final static String URL_STATUS_TASMOTA = "http://%IP:%PORT/cm?cmnd=Power%ID%20STATUS";
    private final static String URL_STATUS_SHELLY_PRO = "http://%IP:%PORT/rpc/Switch.GetStatus?id=%ID";
    private final static String URL_TOGGLE_TASMOTA = "http://%IP:%PORT/cm?cmnd=Power%ID%20%TOGGLE";
    private final static String URL_TOGGLE_SHELLY_PRO = "http://%IP:%PORT/rpc/Switch.Toggle?id=%ID";
    public final static RelayType RELAY_TYPE_TASMOTA = RelayType.builder()
            .id(NumberUtils.LONG_ONE)
            .name("Relay type TASMOTA entity")
            .urlStatus(URL_STATUS_TASMOTA)
            .urlToggle(URL_TOGGLE_TASMOTA)
            .turnOn(RelayConstants.PORT_STATUS_ON)
            .turnOff(RelayConstants.PORT_STATUS_OFF)
            .deviceTypeString(RelayConstants.DEVICE_TYPE_TASMOTA)
            .build();
    public final static RelayType RELAY_TYPE_SHELLY = RelayType.builder()
            .id(NumberUtils.LONG_ONE)
            .name("Relay type SHELLY entity")
            .urlStatus(URL_STATUS_SHELLY_PRO)
            .urlToggle(URL_TOGGLE_SHELLY_PRO)
            .turnOn(Boolean.toString(true))
            .turnOff(Boolean.toString(false))
            .deviceTypeString(RelayConstants.DEVICE_TYPE_SHELLY_PRO)
            .build();

    public final static RelayTypeDtoIn RELAY_TYPE_DTO_IN_TASMOTA = RelayTypeDtoIn.builder()
            .id(null)
            .name("Relay type TASMOTA DTO IN")
            .urlStatus(URL_STATUS_TASMOTA)
            .urlToggle(URL_TOGGLE_TASMOTA)
            .turnOn(RelayConstants.PORT_STATUS_ON)
            .turnOff(RelayConstants.PORT_STATUS_OFF)
            .deviceTypeString(RelayConstants.DEVICE_TYPE_TASMOTA)
            .build();

    public final static RelayTypeDtoIn RELAY_TYPE_DTO_IN_SHELLY = RelayTypeDtoIn.builder()
            .id(null)
            .name("Relay type SHELLY PRO DTO IN")
            .urlStatus(URL_STATUS_SHELLY_PRO)
            .urlToggle(URL_TOGGLE_SHELLY_PRO)
            .turnOn(Boolean.toString(true))
            .turnOff(Boolean.toString(false))
            .deviceTypeString(RelayConstants.DEVICE_TYPE_SHELLY_PRO)
            .build();

    @ParameterizedTest
    @MethodSource("provideEntity")
    void toDtoOut(RelayType relayType) {
        Assertions.assertThat(relayTypeMapper.toDtoOut(relayType))
                .returns(relayType.getId(), RelayTypeDtoOut::getId)
                .returns(relayType.getName(), RelayTypeDtoOut::getName)
                .returns(relayType.getUrlStatus(), RelayTypeDtoOut::getUrlStatusTemplate)
                .returns(relayType.getUrlToggle(), RelayTypeDtoOut::getUrlToggleTemplate)
                .returns(relayType.getDeviceTypeString(), RelayTypeDtoOut::getDeviceTypeString);
    }

    @ParameterizedTest
    @MethodSource("provideDtoIn")
    void toEntity(RelayTypeDtoIn relayTypeDtoIn) {
        Assertions.assertThat(relayTypeMapper.toEntity(relayTypeDtoIn))
                .returns(relayTypeDtoIn.getId(), RelayType::getId)
                .returns(relayTypeDtoIn.getName(), RelayType::getName)
                .returns(relayTypeDtoIn.getUrlStatus(), RelayType::getUrlStatus)
                .returns(relayTypeDtoIn.getUrlToggle(), RelayType::getUrlToggle)
                .returns(relayTypeDtoIn.getDeviceTypeString(), RelayType::getDeviceTypeString);
    }

    private static Stream<Arguments> provideDtoIn() {
        return Stream.of(
                Arguments.of(RELAY_TYPE_DTO_IN_TASMOTA),
                Arguments.of(RELAY_TYPE_DTO_IN_SHELLY)
        );
    }

    private static Stream<Arguments> provideEntity() {
        return Stream.of(
                Arguments.of(RELAY_TYPE_SHELLY),
                Arguments.of(RELAY_TYPE_TASMOTA)
        );
    }

}