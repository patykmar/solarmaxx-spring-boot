package cz.patyk.solarmaxx.backend.service;

import cz.patyk.solarmaxx.backend.dto.data.RelayOutputDataDto;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class AdapterService {
    static final String HTTP = "http://";
    static final String HTTPS = "https://";

    public URI createInsecureBasicUrl(RelayOutputDataDto relayOutputDataDto) {
        String relayIpAddress = relayOutputDataDto.getRelayIpAddress();
        String relayPort = String.valueOf(relayOutputDataDto.getRelayPort());
        return URI.create(HTTP.concat(relayIpAddress).concat(":").concat(relayPort));
    }

    public URI createSecureBasicUrl(String ip) {
        return URI.create(HTTPS.concat(ip));
    }

    public Boolean convertOutputStatusToBoolean(OutputStatus outputStatus) {
        return OutputStatus.ON.equals(outputStatus);
    }
}
