package cz.patyk.solarmaxx.backend.repository;

import cz.patyk.solarmaxx.backend.entity.Relay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelayRepository extends JpaRepository<Relay, Long> {
}
