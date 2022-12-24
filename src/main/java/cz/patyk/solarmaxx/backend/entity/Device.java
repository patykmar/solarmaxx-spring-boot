package cz.patyk.solarmaxx.backend.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Exclude
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @ToString.Exclude
    @JoinColumn(name = "device_type_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private DeviceType deviceType;
    private String name;
    private String ipAddress;
    private Short port;
    private Short outputCount;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "device")
    private List<DeviceSchedule> deviceSchedules;
}
