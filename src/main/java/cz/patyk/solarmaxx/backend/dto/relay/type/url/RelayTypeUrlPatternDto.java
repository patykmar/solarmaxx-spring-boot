package cz.patyk.solarmaxx.backend.dto.relay.type.url;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelayTypeUrlPatternDto {
    //TODO: consider to migrate to application.yml
    private String ip;
    private String id;
    private String port;
    private String toggle;
}