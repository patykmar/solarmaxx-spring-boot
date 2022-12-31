package cz.patyk.solarmaxx.backend.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
public class RelaySchedule implements Serializable, IEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "relay_id", nullable = false)
    private Relay relay;
    private LocalDateTime onStart;
    private LocalDateTime onEnd;
    private Byte dayNumber;
}
