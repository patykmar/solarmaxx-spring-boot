package cz.patyk.solarmaxx.backend.entity;

import cz.patyk.solarmaxx.backend.dto.relay.output.OutputStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelayOutput implements Serializable, IEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Byte outputId;
    @Enumerated(EnumType.STRING)
    private OutputStatus outputStatus;
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "relay_id", nullable = false)
    private Relay relay;
}
