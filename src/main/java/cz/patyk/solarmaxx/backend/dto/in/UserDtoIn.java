package cz.patyk.solarmaxx.backend.dto.in;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDtoIn implements IDtoIn{
    private Long id;
    private String email;
    private String roles;
    private String password;
}
