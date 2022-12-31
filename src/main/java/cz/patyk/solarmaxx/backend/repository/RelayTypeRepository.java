package cz.patyk.solarmaxx.backend.repository;

import cz.patyk.solarmaxx.backend.entity.RelayType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelayTypeRepository extends JpaRepository<RelayType, Long> {
}
