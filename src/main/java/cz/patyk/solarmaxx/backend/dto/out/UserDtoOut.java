package cz.patyk.solarmaxx.backend.dto.out;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDtoOut implements IDtoOut{
    private Long id;
    private String email;
    private String roles;
}
