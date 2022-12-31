package cz.patyk.solarmaxx.backend.factory.mapper;

import cz.patyk.solarmaxx.backend.dto.relay.SupportedRelayType;
import cz.patyk.solarmaxx.backend.mapper.relay.type.url.RelayTypeUrlMapper;
import cz.patyk.solarmaxx.backend.mapper.relay.type.url.ShellyProUrlMapper;
import cz.patyk.solarmaxx.backend.mapper.relay.type.url.TasmotaUrlMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RelayTypeUrlFactory {
    private final ShellyProUrlMapper shellyProUrlMapper;
    private final TasmotaUrlMapper tasmotaUrlMapper;

    public RelayTypeUrlMapper getRelayUrlMapper(SupportedRelayType relayType) {
        return switch (relayType) {
            case TASMOTA -> tasmotaUrlMapper;
            case SHELLY_PRO -> shellyProUrlMapper;
        };
    }
}
