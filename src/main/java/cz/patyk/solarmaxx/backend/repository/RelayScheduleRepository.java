package cz.patyk.solarmaxx.backend.repository;

import cz.patyk.solarmaxx.backend.entity.RelaySchedule;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelayScheduleRepository extends JpaRepository<RelaySchedule, Long> {
    List<RelaySchedule> findAllByDayNumber(@Param("dayNumber") Byte dayNumber);
}
