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

import java.util.Calendar;
import java.util.List;

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

    public static final RelayOutputScheduleEntity RELAY_OUTPUT_SCHEDULE = RelayOutputScheduleEntity.builder()
            .id(NumberUtils.LONG_ONE)
            .relayOutput(RELAY_OUTPUT_STATUS_ON)
            .onStart("12:12")
            .onEnd("13:13")
            .dayNumber((byte) Calendar.FRIDAY)
            .build();

    public static RelayOutputScheduleEntity makeRelayOutputScheduleStatusOffEntity(Long id, String onStart, String onEnd) {
        return RelayOutputScheduleEntity.builder()
                .id(id)
                .relayOutput(RELAY_OUTPUT_STATUS_OFF)
                .onStart(onStart)
                .onEnd(onEnd)
                .dayNumber((byte) Calendar.FRIDAY)
                .build();
    }

    public static final List<RelayOutputScheduleEntity> RELAY_OUTPUT_SCHEDULE_ENTITIES = List.of(
            makeRelayOutputScheduleStatusOffEntity(NumberUtils.LONG_ONE, "08:00", "10:00"),
            makeRelayOutputScheduleStatusOffEntity(NumberUtils.LONG_ONE, "12:00", "13:00"),
            makeRelayOutputScheduleStatusOffEntity(NumberUtils.LONG_ONE, "16:00", "16:30"),
            makeRelayOutputScheduleStatusOffEntity(NumberUtils.LONG_ONE, "20:00", "22:00")
    );

}
