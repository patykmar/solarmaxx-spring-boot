package cz.patyk.solarmaxx.backend.mapper.relay.type.url;

import cz.patyk.solarmaxx.backend.config.DeviceTypeUrlPattern;

public class TasmotaUrlMapper extends AbstractRelayTypeUrlMapper {
    public TasmotaUrlMapper(DeviceTypeUrlPattern deviceTypeUrlPattern) {
        super(deviceTypeUrlPattern);
        this.defaultOutputId = 1;
    }

}
