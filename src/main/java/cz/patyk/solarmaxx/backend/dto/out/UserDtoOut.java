package cz.patyk.solarmaxx.backend.dto.out;

import lombok.Data;

@Data
public class UserDtoOut {
    private Long id;
    private String email;
    private String roles;
}
