package cz.patyk.solarmaxx;

import cz.patyk.solarmaxx.backend.dto.relay.RelayConstants;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.entity.RelayOutput;
import cz.patyk.solarmaxx.backend.entity.RelayOutputSchedule;
import cz.patyk.solarmaxx.backend.entity.RelayType;
import cz.patyk.solarmaxx.backend.entity.User;
import cz.patyk.solarmaxx.backend.mapper.relay.TestRelayConstants;
import cz.patyk.solarmaxx.constants.RelayEntityConstants;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Calendar;

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
    public static final RelayOutput RELAY_OUTPUT = RelayOutput.builder()
            .id(NumberUtils.LONG_ONE)
            .description("Relay output description")
            .outputId(NumberUtils.INTEGER_ONE)
            .outputStatus(OutputStatus.ON)
            .relay(RelayEntityConstants.createTasmotaOwnedByAdminOutputOn())
            .build();

    public static final RelayOutputSchedule RELAY_OUTPUT_SCHEDULE = RelayOutputSchedule.builder()
            .id(NumberUtils.LONG_ONE)
            .relayOutput(RELAY_OUTPUT)
            .onStart("12:12")
            .onEnd("13:13")
            .dayNumber((byte) Calendar.FRIDAY)
            .build();
}
