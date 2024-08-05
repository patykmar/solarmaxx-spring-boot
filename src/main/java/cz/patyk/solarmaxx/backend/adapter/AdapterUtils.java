package cz.patyk.solarmaxx.backend.adapter;

import cz.patyk.solarmaxx.backend.dto.data.RelayOutputDataDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.URI;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdapterUtils {
    public static final String HTTP = "http://";
    public static final String HTTPS = "https://";

    public static URI createInsecureBasicUrl(RelayOutputDataDto relayOutputDataDto) {
        String relayIpAddress = relayOutputDataDto.getRelayIpAddress();
        String relayPort = String.valueOf(relayOutputDataDto.getRelayPort());
        return URI.create(AdapterUtils.HTTP.concat(relayIpAddress).concat(":").concat(relayPort));
    }

    public static URI createSecureBasicUrl(String ip) {
        return URI.create(AdapterUtils.HTTPS.concat(ip));
    }
}
