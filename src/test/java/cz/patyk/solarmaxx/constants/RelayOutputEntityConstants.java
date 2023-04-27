package cz.patyk.solarmaxx.constants;

import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.entity.Relay;
import cz.patyk.solarmaxx.backend.entity.RelayOutput;

import java.util.List;

import static org.apache.commons.lang3.math.NumberUtils.BYTE_ZERO;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;

public class RelayOutputEntityConstants {

    private static final byte BYTE_TWO = (byte) 2;
    private static final byte BYTE_THREE = (byte) 3;
    private static final byte BYTE_FOUR = (byte) 4;


    public static final RelayOutput RELAY_OUTPUT_TASMOTA_ON_010 =
            getRelayOutput(LONG_ONE, BYTE_ZERO, OutputStatus.NA, RelayEntityConstants.RELAY_TASMOTA_ADMIN_ON);
    public static final List<RelayOutput> RELAY_OUTPUT_TASMOTA_LIST_ON =
            getRelayOutputListFromOne(RelayEntityConstants.RELAY_TASMOTA_ADMIN_ON, OutputStatus.ON);
    public static final List<RelayOutput> RELAY_OUTPUT_SHELLY_LIST_OFF =
            getRelayOutputListFromZero(RelayEntityConstants.RELAY_SHELLY_PRO_ADMIN_OFF, OutputStatus.OFF);

    public static RelayOutput getRelayOutput(Long id, Byte outputId, OutputStatus outputStatus, Relay relay) {
        return RelayOutput.builder()
                .id(id)
                .description("Relay output description " + id)
                .outputId(outputId)
                .outputStatus(outputStatus)
                .relay(relay)
                .build();
    }

    public static List<RelayOutput> getRelayOutputListNa(Relay relay) {
        return getRelayOutputListFromZero(relay, OutputStatus.NA);
    }

    public static List<RelayOutput> getRelayOutputListOn(Relay relay) {
        return getRelayOutputListFromZero(relay, OutputStatus.ON);
    }

    public static List<RelayOutput> getRelayOutputListOff(Relay relay) {
        return getRelayOutputListFromZero(relay, OutputStatus.OFF);
    }

    public static List<RelayOutput> getRelayOutputListFromZero(Relay relay, OutputStatus outputStatus) {
        return List.of(
                getRelayOutput(1L, BYTE_ZERO, outputStatus, relay),
                getRelayOutput(2L, BYTE_ONE, outputStatus, relay),
                getRelayOutput(3L, BYTE_TWO, outputStatus, relay),
                getRelayOutput(4L, BYTE_THREE, outputStatus, relay)
        );
    }

    public static List<RelayOutput> getRelayOutputListFromOne(Relay relay, OutputStatus outputStatus) {
        return List.of(
                getRelayOutput(1L, BYTE_ONE, outputStatus, relay),
                getRelayOutput(2L, BYTE_TWO, outputStatus, relay),
                getRelayOutput(3L, BYTE_THREE, outputStatus, relay),
                getRelayOutput(4L, BYTE_FOUR, outputStatus, relay)
        );
    }

    private RelayOutputEntityConstants() {
    }
}
