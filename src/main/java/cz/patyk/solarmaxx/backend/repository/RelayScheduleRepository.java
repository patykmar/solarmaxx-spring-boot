package cz.patyk.solarmaxx.backend.repository;

import cz.patyk.solarmaxx.backend.entity.RelaySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelayScheduleRepository extends JpaRepository<RelaySchedule, Long> {
}
