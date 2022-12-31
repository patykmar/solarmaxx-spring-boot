package cz.patyk.solarmaxx.backend.mapper.relay.type.output;

import cz.patyk.solarmaxx.backend.dto.relay.output.RelayOutputDto;
import cz.patyk.solarmaxx.backend.entity.Relay;
import cz.patyk.solarmaxx.backend.mapper.relay.type.url.TasmotaUrlMapper;
import cz.patyk.solarmaxx.backend.mapper.relay.type.url.UrlParameterMapper;

import java.util.ArrayList;
import java.util.List;

import static cz.patyk.solarmaxx.backend.dto.relay.type.RelayTypeConstants.TASMOTA_DEFAULT_OUTPUT_ID;

public class TasmotaOutputIdMapper extends OutputIdMapper {

    public TasmotaOutputIdMapper(UrlParameterMapper urlParameterMapper, TasmotaUrlMapper tasmotaUrlMapper) {
        super(urlParameterMapper, tasmotaUrlMapper);
    }

    @Override
    public List<RelayOutputDto> getDeviceOutputs(Relay relay, boolean offlineMode) {
        List<RelayOutputDto> relayOutputDtoList = new ArrayList<>();

        for (byte i = TASMOTA_DEFAULT_OUTPUT_ID; i <= relay.getOutputCount(); i++) {
            relayOutputDtoList.add(toRelayOutputDto(relay, i));
        }
        return relayOutputDtoList;
    }

    @Override
    public List<RelayOutputDto> getDeviceOutputs(Relay relay) {
        return getDeviceOutputs(relay, false);
    }

}
