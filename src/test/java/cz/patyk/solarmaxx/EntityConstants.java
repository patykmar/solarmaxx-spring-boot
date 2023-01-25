package cz.patyk.solarmaxx;

import cz.patyk.solarmaxx.backend.dto.relay.RelayConstants;
import cz.patyk.solarmaxx.backend.entity.Relay;
import cz.patyk.solarmaxx.backend.entity.RelaySchedule;
import cz.patyk.solarmaxx.backend.entity.RelayType;
import cz.patyk.solarmaxx.backend.entity.User;
import cz.patyk.solarmaxx.backend.mapper.relay.TestRelayConstants;
import org.apache.commons.lang3.math.NumberUtils;

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
    public static final Relay RELAY_TASMOTA_ADMIN = Relay.builder()
            .id(NumberUtils.LONG_ONE)
            .name("Relay TASMOTA with user type ADMIN")
            .ipAddress(TestRelayConstants.RELAY_IP)
            .port(TestRelayConstants.RELAY_PORT)
            .outputCount(TestRelayConstants.RELAY_OUTPUT_COUNT)
            .user(USER_ADMIN)
            .relayType(RELAY_TYPE_TASMOTA)
            .build();
    public static final Relay RELAY_SHELLY_PRO_ADMIN = Relay.builder()
            .id(NumberUtils.LONG_ONE)
            .name("Relay Shelly Pro with user type ADMIN")
            .ipAddress(TestRelayConstants.RELAY_IP)
            .port(TestRelayConstants.RELAY_PORT)
            .outputCount(TestRelayConstants.RELAY_OUTPUT_COUNT)
            .user(USER_ADMIN)
            .relayType(RELAY_TYPE_SHELLY)
            .build();

    public static final RelaySchedule RELAY_SCHEDULE_TASMOTA = RelaySchedule.builder()
            .id(NumberUtils.LONG_ONE)
            .relay(RELAY_TASMOTA_ADMIN)
            .outputId(NumberUtils.BYTE_ONE)
            .onStart(ValueConstants.RELAY_SCHEDULE_ON_START)
            .onEnd(ValueConstants.RELAY_SCHEDULE_ON_END)
            .dayNumber(NumberUtils.BYTE_ONE)
            .build();
}
