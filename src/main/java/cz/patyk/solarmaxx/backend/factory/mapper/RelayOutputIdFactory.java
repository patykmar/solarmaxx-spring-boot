package cz.patyk.solarmaxx.backend.factory.mapper;

import cz.patyk.solarmaxx.backend.dto.relay.SupportedRelayType;
import cz.patyk.solarmaxx.backend.mapper.relay.type.output.OutputIdMapper;
import cz.patyk.solarmaxx.backend.mapper.relay.type.output.ShellyProOutputIdMapper;
import cz.patyk.solarmaxx.backend.mapper.relay.type.output.TasmotaOutputIdMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RelayOutputIdFactory {
    private final ShellyProOutputIdMapper shellyProOutputIdMapper;
    private final TasmotaOutputIdMapper tasmotaOutputIdMapper;

    public OutputIdMapper getOutputIdMapper(SupportedRelayType relayType) {
        return switch (relayType) {
            case TASMOTA -> tasmotaOutputIdMapper;
            case SHELLY_PRO -> shellyProOutputIdMapper;
        };
    }

}
