package cz.patyk.solarmaxx.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Entity
public class DeviceSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @JoinColumn(name = "device_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Device device;
    private LocalDateTime onStart;
    private LocalDateTime onEnd;
    private Byte dayNumber;
}
