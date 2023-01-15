package cz.patyk.solarmaxx.backend.factory.adapter;

import cz.patyk.solarmaxx.backend.adapter.RelayAdapter;
import cz.patyk.solarmaxx.backend.adapter.ShellyRelayAdapter;
import cz.patyk.solarmaxx.backend.adapter.TasmotaRelayAdapter;
import cz.patyk.solarmaxx.backend.dto.relay.SupportedRelayType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RelayAdapterFactory {
    private final TasmotaRelayAdapter tasmotaRelayAdapter;
    private final ShellyRelayAdapter shellyRelayAdapter;

    public RelayAdapter getRelayAdapter(SupportedRelayType relayType) {
        return switch (relayType) {
            case TASMOTA -> tasmotaRelayAdapter;
            case SHELLY_PRO -> shellyRelayAdapter;
        };
    }
}
