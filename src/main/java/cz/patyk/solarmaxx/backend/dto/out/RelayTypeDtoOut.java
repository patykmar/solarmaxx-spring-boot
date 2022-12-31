package cz.patyk.solarmaxx.backend.dto.out;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelayTypeDtoOut implements IDtoOut {
    private Long id;
    private String name;
    private String urlStatusTemplate;
    private String urlToggleTemplate;
    private String deviceTypeString;
}
