package cz.patyk.solarmaxx.backend.mapper.relay.type;

import cz.patyk.solarmaxx.DtoInConstants;
import cz.patyk.solarmaxx.EntityConstants;
import cz.patyk.solarmaxx.backend.dto.in.RelayTypeDtoIn;
import cz.patyk.solarmaxx.backend.dto.out.RelayTypeDtoOut;
import cz.patyk.solarmaxx.backend.entity.RelayType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;

import java.util.stream.Stream;

class RelayTypeMapperTest {
    private final RelayTypeMapper relayTypeMapper = Mappers.getMapper(RelayTypeMapper.class);

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
                Arguments.of(DtoInConstants.RELAY_TYPE_DTO_IN_TASMOTA),
                Arguments.of(DtoInConstants.RELAY_TYPE_DTO_IN_SHELLY)
        );
    }

    private static Stream<Arguments> provideEntity() {
        return Stream.of(
                Arguments.of(EntityConstants.RELAY_TYPE_SHELLY),
                Arguments.of(EntityConstants.RELAY_TYPE_TASMOTA)
        );
    }

}