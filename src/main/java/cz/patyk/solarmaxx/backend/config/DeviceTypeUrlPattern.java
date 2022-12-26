package cz.patyk.solarmaxx.backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "device.type.url.pattern")
public class DeviceTypeUrlPattern {
    private String ip;
    private String id;
    private String port;
    private String toggle;
}
