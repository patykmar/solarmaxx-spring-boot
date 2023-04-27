package cz.patyk.solarmaxx.constants;

import cz.patyk.solarmaxx.EntityConstants;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.entity.Relay;
import cz.patyk.solarmaxx.backend.entity.RelayType;
import cz.patyk.solarmaxx.backend.entity.User;
import cz.patyk.solarmaxx.backend.mapper.relay.TestRelayConstants;
import org.apache.commons.lang3.math.NumberUtils;

public class RelayEntityConstants {

    private RelayEntityConstants() {
    }

    public static final Relay RELAY_TASMOTA_ADMIN_ON = createTasmotaOwnedByAdminOutputOn();
    public static final Relay RELAY_SHELLY_PRO_ADMIN_ON = createShellyProOwnedByAdminOutputOn();
    public static final Relay RELAY_SHELLY_PRO_ADMIN_OFF = createShellyProOwnedByAdminOutputOff();

    public static Relay createTasmotaOwnedByAdminOutputOn() {
        Relay relay = createTasmota(EntityConstants.USER_ADMIN);
        relay.setRelayOutputs(RelayOutputEntityConstants.getRelayOutputListFromZero(relay, OutputStatus.ON));
        return relay;
    }

    public static Relay createShellyProOwnedByAdminOutputOn() {
        Relay relay = createShellyPro(EntityConstants.USER_ADMIN);
        relay.setRelayOutputs(RelayOutputEntityConstants.getRelayOutputListFromZero(relay, OutputStatus.ON));
        return relay;
    }

    public static Relay createShellyProOwnedByAdminOutputOff() {
        Relay relay = createShellyPro(EntityConstants.USER_ADMIN);
        relay.setRelayOutputs(RelayOutputEntityConstants.getRelayOutputListFromZero(relay, OutputStatus.OFF));
        return relay;
    }

    public static Relay createTasmota(User user) {
        return createRelay(NumberUtils.LONG_ONE, user, EntityConstants.RELAY_TYPE_TASMOTA);
    }

    public static Relay createShellyPro(User user) {
        return createRelay(NumberUtils.LONG_ONE, user, EntityConstants.RELAY_TYPE_SHELLY);
    }

    public static Relay createRelay(Long id, User user, RelayType relayType) {
        String name = String.format("Relay %s with user type %s",
                relayType.getDeviceTypeString(),
                user.getRoles()
        );

        return Relay.builder()
                .id(id)
                .name(name)
                .ipAddress(TestRelayConstants.RELAY_IP)
                .port(TestRelayConstants.RELAY_PORT)
                .outputCount(TestRelayConstants.RELAY_OUTPUT_COUNT)
                .user(user)
                .relayType(relayType)
                .build();
    }

}
