package cz.patyk.solarmaxx.constants;

import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import cz.patyk.solarmaxx.backend.entity.Relay;
import cz.patyk.solarmaxx.backend.entity.RelayOutput;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.List;

public class RelayOutputEntityConstants {

    private static final Integer INTEGER_TWO = 2;
    private static final Integer INTEGER_THREE = 3;
    private static final Integer INTEGER_FOUR = 4;


    public static final RelayOutput RELAY_OUTPUT_TASMOTA_ON_010 =
            getRelayOutput(NumberUtils.LONG_ONE, NumberUtils.INTEGER_ZERO, OutputStatus.NA, RelayEntityConstants.RELAY_TASMOTA_ADMIN_ON);
    public static final List<RelayOutput> RELAY_OUTPUT_TASMOTA_LIST_ON =
            getRelayOutputListFromOne(RelayEntityConstants.RELAY_TASMOTA_ADMIN_ON, OutputStatus.ON);
    public static final List<RelayOutput> RELAY_OUTPUT_SHELLY_LIST_OFF =
            getRelayOutputListFromZero(RelayEntityConstants.RELAY_SHELLY_PRO_ADMIN_OFF, OutputStatus.OFF);

    public static RelayOutput getRelayOutput(Long id, Integer outputId, OutputStatus outputStatus, Relay relay) {
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
                getRelayOutput(1L, NumberUtils.INTEGER_ZERO, outputStatus, relay),
                getRelayOutput(2L, NumberUtils.INTEGER_ONE, outputStatus, relay),
                getRelayOutput(3L, INTEGER_TWO, outputStatus, relay),
                getRelayOutput(4L, INTEGER_THREE, outputStatus, relay)
        );
    }

    public static List<RelayOutput> getRelayOutputListFromOne(Relay relay, OutputStatus outputStatus) {
        return List.of(
                getRelayOutput(1L, NumberUtils.INTEGER_ZERO, outputStatus, relay),
                getRelayOutput(2L, INTEGER_TWO, outputStatus, relay),
                getRelayOutput(3L, INTEGER_THREE, outputStatus, relay),
                getRelayOutput(4L, INTEGER_FOUR, outputStatus, relay)
        );
    }

    private RelayOutputEntityConstants() {
    }
}
