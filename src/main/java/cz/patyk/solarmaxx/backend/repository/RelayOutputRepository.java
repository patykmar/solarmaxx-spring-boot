package cz.patyk.solarmaxx.backend.repository;

import cz.patyk.solarmaxx.backend.entity.RelayOutput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelayOutputRepository extends JpaRepository<RelayOutput, Long> {
    List<RelayOutput> findAllByRelayId(Long relayId);
}
