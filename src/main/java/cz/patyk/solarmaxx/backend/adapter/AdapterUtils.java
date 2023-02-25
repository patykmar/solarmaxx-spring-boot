package cz.patyk.solarmaxx.backend.adapter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.URI;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdapterUtils {
    public static final String HTTP = "http://";
    public static final String HTTPS = "https://";

    public static URI createInsecureBasicUrl(String ip) {
        return URI.create(AdapterUtils.HTTP.concat(ip));
    }

    public static URI createSecureBasicUrl(String ip) {
        return URI.create(AdapterUtils.HTTPS.concat(ip));
    }
}
