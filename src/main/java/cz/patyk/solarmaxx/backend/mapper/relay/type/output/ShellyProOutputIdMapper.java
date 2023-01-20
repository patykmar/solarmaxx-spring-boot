package cz.patyk.solarmaxx.backend.mapper.relay.type.output;

import cz.patyk.solarmaxx.backend.adapter.ShellyProRelayAdapter;
import cz.patyk.solarmaxx.backend.dto.relay.output.RelayOutputDto;
import cz.patyk.solarmaxx.backend.entity.Relay;
import cz.patyk.solarmaxx.backend.mapper.relay.type.url.ShellyProUrlMapper;
import cz.patyk.solarmaxx.backend.mapper.relay.type.url.UrlParameterMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static cz.patyk.solarmaxx.backend.dto.relay.type.RelayTypeConstants.SHALLY_PRO_DEFAULT_OUTPUT_ID;

@Component
public class ShellyProOutputIdMapper extends OutputIdMapper {

    public ShellyProOutputIdMapper(
            UrlParameterMapper urlParameterMapper,
            ShellyProUrlMapper shellyProUrlMapper,
            ShellyProRelayAdapter shellyProRelayAdapter
    ) {
        super(urlParameterMapper, shellyProUrlMapper, shellyProRelayAdapter);
    }

    @Override
    public List<RelayOutputDto> getDeviceOutputs(Relay relay, boolean onlineMode) {
        List<RelayOutputDto> relayOutputDtoList = new ArrayList<>();

        for (byte i = SHALLY_PRO_DEFAULT_OUTPUT_ID; i < relay.getOutputCount(); i++) {
            relayOutputDtoList.add(toRelayOutputDto(relay, i, onlineMode));
        }
        return relayOutputDtoList;
    }

    @Override
    public List<RelayOutputDto> getDeviceOutputs(Relay relay) {
        return getDeviceOutputs(relay, false);
    }

}
