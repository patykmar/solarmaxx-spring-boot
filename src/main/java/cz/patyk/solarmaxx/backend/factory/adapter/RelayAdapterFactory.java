package cz.patyk.solarmaxx.backend.factory.adapter;

import cz.patyk.solarmaxx.backend.adapter.RelayAdapter;
import cz.patyk.solarmaxx.backend.adapter.ShellyProRelayAdapter;
import cz.patyk.solarmaxx.backend.adapter.TasmotaRelayAdapter;
import cz.patyk.solarmaxx.backend.dto.relay.SupportedRelayType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RelayAdapterFactory {
    private final TasmotaRelayAdapter tasmotaRelayAdapter;
    private final ShellyProRelayAdapter shellyProRelayAdapter;

    public RelayAdapter getRelayAdapter(SupportedRelayType relayType) {
        return switch (relayType) {
            case TASMOTA -> tasmotaRelayAdapter;
            case SHELLY_PRO -> shellyProRelayAdapter;
        };
    }
}
