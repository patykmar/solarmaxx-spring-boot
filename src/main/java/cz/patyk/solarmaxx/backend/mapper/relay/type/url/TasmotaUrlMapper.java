package cz.patyk.solarmaxx.backend.mapper.relay.type.url;

import cz.patyk.solarmaxx.backend.dto.relay.type.url.RelayTypeUrlPatternDto;
import cz.patyk.solarmaxx.backend.dto.relay.type.RelayTypeConstants;
import org.springframework.stereotype.Component;

@Component
public class TasmotaUrlMapper extends AbstractRelayTypeUrlMapper {
    public TasmotaUrlMapper(RelayTypeUrlPatternDto relayTypeUrlPatternDto) {
        super(relayTypeUrlPatternDto);
        this.defaultOutputId = RelayTypeConstants.TASMOTA_DEFAULT_OUTPUT_ID;
    }

}
