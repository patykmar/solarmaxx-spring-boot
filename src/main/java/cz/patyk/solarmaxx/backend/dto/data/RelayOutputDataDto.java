package cz.patyk.solarmaxx.backend.dto.data;

import cz.patyk.solarmaxx.backend.dto.relay.SupportedRelayType;
import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelayOutputDataDto implements DtoDataInterface {
    private Long id;
    private String description;
    private Integer outputId;
    private OutputStatus outputStatus;
    private OutputStatus deviceOutputStatus;
    private Long relayId;
    private String relayName;
    private String relayIpAddress;
    private Integer relayPort;
    private Long relayTypeId;
    private String relayTypeName;
    private String relayTypeString;
    private SupportedRelayType relayTypeEnum;
}
