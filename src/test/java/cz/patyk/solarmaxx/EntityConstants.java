package cz.patyk.solarmaxx;

import cz.patyk.solarmaxx.backend.dto.relay.RelayConstants;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.entity.RelayOutput;
import cz.patyk.solarmaxx.backend.entity.RelayOutputScheduleEntity;
import cz.patyk.solarmaxx.backend.entity.RelayType;
import cz.patyk.solarmaxx.backend.entity.User;
import cz.patyk.solarmaxx.backend.mapper.relay.TestRelayConstants;
import cz.patyk.solarmaxx.constants.RelayEntityConstants;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class EntityConstants {
    private EntityConstants() {
    }

    public final static RelayType RELAY_TYPE_TASMOTA = RelayType.builder()
            .id(NumberUtils.LONG_ONE)
            .name("Relay type TASMOTA entity")
            .urlStatus(TestRelayConstants.URL_STATUS_TASMOTA)
            .urlToggle(TestRelayConstants.URL_TOGGLE_TASMOTA)
            .turnOn(RelayConstants.PORT_STATUS_ON)
            .turnOff(RelayConstants.PORT_STATUS_OFF)
            .deviceTypeString(RelayConstants.DEVICE_TYPE_TASMOTA)
            .build();
    public final static RelayType RELAY_TYPE_SHELLY = RelayType.builder()
            .id(NumberUtils.LONG_ONE)
            .name("Relay type SHELLY entity")
            .urlStatus(TestRelayConstants.URL_STATUS_SHELLY_PRO)
            .urlToggle(TestRelayConstants.URL_TOGGLE_SHELLY_PRO)
            .turnOn(Boolean.toString(true))
            .turnOff(Boolean.toString(false))
            .deviceTypeString(RelayConstants.DEVICE_TYPE_SHELLY_PRO)
            .build();
    public static final User USER_ADMIN = User.builder()
            .id(NumberUtils.LONG_ONE)
            .email(ValueConstants.USER_FAKE_EMAIL)
            .roles(ValueConstants.USER_ROLE_ADMIN)
            .password(ValueConstants.USER_PASSWORD)
            .build();
    public static final RelayOutput RELAY_OUTPUT_STATUS_ON = RelayOutput.builder()
            .id(NumberUtils.LONG_ONE)
            .description("Relay output description")
            .outputId(NumberUtils.BYTE_ONE)
            .outputStatus(OutputStatus.ON)
            .relay(RelayEntityConstants.createTasmotaOwnedByAdminOutputOn())
            .build();
    public static final RelayOutput RELAY_OUTPUT_STATUS_OFF = RelayOutput.builder()
            .id(NumberUtils.LONG_ONE)
            .description("Relay output description")
            .outputId(NumberUtils.BYTE_ONE)
            .outputStatus(OutputStatus.OFF)
            .relay(RelayEntityConstants.createTasmotaOwnedByAdminOutputOn())
            .build();

    public static final RelayOutputScheduleEntity RELAY_OUTPUT_SCHEDULE =
            makeRelayOutputScheduleStatusOnEntity(NumberUtils.LONG_ONE, "12:12", "13:13");

    public static RelayOutputScheduleEntity makeRelayOutputScheduleStatusOffEntity(Long id, String onStart, String onEnd) {
        return RelayOutputScheduleEntity.builder()
                .id(id)
                .relayOutput(RELAY_OUTPUT_STATUS_OFF)
                .onStart(onStart)
                .onEnd(onEnd)
                .dayNumber((byte) Calendar.FRIDAY)
                .build();
    }

    public static RelayOutputScheduleEntity makeRelayOutputScheduleStatusOnEntity(Long id, String onStart, String onEnd) {
        return RelayOutputScheduleEntity.builder()
                .id(id)
                .relayOutput(RELAY_OUTPUT_STATUS_ON)
                .onStart(onStart)
                .onEnd(onEnd)
                .dayNumber((byte) Calendar.FRIDAY)
                .build();
    }

    public static final List<RelayOutputScheduleEntity> RELAY_OUTPUT_SCHEDULE_ENTITIES = List.of(
            makeRelayOutputScheduleStatusOnEntity(NumberUtils.LONG_ONE, "00:00", "10:10"),
            makeRelayOutputScheduleStatusOnEntity(2L, "10:10", "13:59"),
            makeRelayOutputScheduleStatusOffEntity(3L, "10:10", "10:10"),
            makeRelayOutputScheduleStatusOffEntity(4L, "10:00", "11:59"),
            makeRelayOutputScheduleStatusOffEntity(5L, "20:00", "23:59")
    );

}
