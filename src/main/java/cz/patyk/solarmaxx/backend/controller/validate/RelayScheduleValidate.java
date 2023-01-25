package cz.patyk.solarmaxx.backend.controller.validate;

import cz.patyk.solarmaxx.backend.entity.Relay;
import org.springframework.stereotype.Component;

@Component
public class RelayScheduleValidate {

    public boolean validateOutputId(Byte outputId, Relay relay){
        boolean isOutputIdEqualOrGreaterThenOne = (1 <= outputId);
        boolean isOutputIdLessOrEqualThanRelayOutputCount = (outputId <= relay.getOutputCount());

        return isOutputIdEqualOrGreaterThenOne && isOutputIdLessOrEqualThanRelayOutputCount;
    }
}
