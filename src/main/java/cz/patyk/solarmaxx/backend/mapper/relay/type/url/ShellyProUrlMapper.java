package cz.patyk.solarmaxx.backend.mapper.relay.type.url;

import cz.patyk.solarmaxx.backend.dto.relay.type.url.parameter.ToggleUrlParameter;
import cz.patyk.solarmaxx.backend.dto.relay.type.RelayTypeConstants;
import cz.patyk.solarmaxx.backend.dto.relay.type.url.RelayTypeUrlPatternDto;

public class ShellyProUrlMapper extends AbstractRelayTypeUrlMapper {
    public ShellyProUrlMapper(RelayTypeUrlPatternDto relayTypeUrlPatternDto) {
        super(relayTypeUrlPatternDto);
        this.defaultOutputId = RelayTypeConstants.SHALLY_PRO_DEFAULT_OUTPUT_ID;
    }

    @Override
    public String toRealUrlToggle(ToggleUrlParameter toggleUrlParameter) {
        //Shelly pro doesn't have toggle URL
        return super.toRealUrlStatus(toggleUrlParameter);
    }
}
