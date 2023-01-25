package cz.patyk.solarmaxx.backend.controller.validate;

import cz.patyk.solarmaxx.backend.entity.Relay;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class RelayScheduleValidateTest {

    @ParameterizedTest
    @MethodSource("provideRelayScheduleParameters")
    void validateOutputId(Byte outputId, Relay relay, boolean validate) {
        RelayScheduleValidate relayScheduleValidate = new RelayScheduleValidate();
        Assertions.assertEquals(validate, relayScheduleValidate.validateOutputId(outputId, relay));
    }

    private static Stream<Arguments> provideRelayScheduleParameters() {
        return Stream.of(
                Arguments.of(NumberUtils.BYTE_ONE, Relay.builder().outputCount(NumberUtils.BYTE_ONE).build(), true),
                Arguments.of(NumberUtils.BYTE_ONE, Relay.builder().outputCount((byte) 8).build(), true),
                Arguments.of((byte) 7, Relay.builder().outputCount((byte) 8).build(), true),
                Arguments.of((byte) 8, Relay.builder().outputCount((byte) 8).build(), true),
                Arguments.of((byte) 9, Relay.builder().outputCount((byte) 8).build(), false),
                Arguments.of(NumberUtils.BYTE_ZERO, Relay.builder().outputCount((byte) 8).build(), false),
                Arguments.of(Byte.MAX_VALUE, Relay.builder().outputCount(NumberUtils.BYTE_ONE).build(), false),
                Arguments.of(Byte.MIN_VALUE, Relay.builder().outputCount(NumberUtils.BYTE_ONE).build(), false)
        );
    }

}