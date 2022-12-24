package cz.patyk.solarmaxx.backend.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
public class DeviceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deviceType")
    private List<Device> devices;
    private String urlStatus;
    private String urlToggle;
    private String turnOn;
    private String turnOff;
    private String deviceTypeString;
}
