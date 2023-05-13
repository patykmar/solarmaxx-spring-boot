package cz.patyk.solarmaxx.backend.repository;

import cz.patyk.solarmaxx.backend.entity.RelayOutputScheduleEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelayOutputScheduleRepository extends JpaRepository<RelayOutputScheduleEntity, Long> {
    List<RelayOutputScheduleEntity> findAllByDayNumber(@Param("dayNumber") Byte dayNumber);
}
