package cz.patyk.solarmaxx.backend.mapper.relay;

import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import org.springframework.stereotype.Component;

@Component
public class OutputStatusMapper {

    public OutputStatus toOutputStatus(Boolean aBoolean) {
        if (Boolean.TRUE.equals(aBoolean)) {
            return OutputStatus.ON;
        } else if (Boolean.FALSE.equals(aBoolean)) {
            return OutputStatus.OFF;
        } else {
            return OutputStatus.NA;
        }
    }

    public OutputStatus toOutputStatusReverse(Boolean aBoolean) {
        if (Boolean.TRUE.equals(aBoolean)) {
            return OutputStatus.OFF;
        } else if (Boolean.FALSE.equals(aBoolean)) {
            return OutputStatus.ON;
        } else {
            return OutputStatus.NA;
        }
    }
}
