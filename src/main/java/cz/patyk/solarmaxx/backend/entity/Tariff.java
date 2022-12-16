package cz.patyk.solarmaxx.backend.entity;

import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Tariff {
    private Long id;
    private User user;
    private String name;
    private Long price;
    private LocalDateTime availableFrom;
    private LocalDateTime availableTo;
}
