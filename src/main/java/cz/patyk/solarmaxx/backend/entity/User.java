package cz.patyk.solarmaxx.backend.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class User {
    private Long id;
    private String email;
    private String role;
    private String password;
}
