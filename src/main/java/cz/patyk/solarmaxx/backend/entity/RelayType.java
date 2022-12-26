package cz.patyk.solarmaxx.backend.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Builder
public class RelayType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "relayType")
    private List<Relay> relays;
    private String urlStatus;
    private String urlToggle;
    private String turnOn;
    private String turnOff;
    private String deviceTypeString;
}
