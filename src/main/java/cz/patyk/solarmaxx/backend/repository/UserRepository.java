package cz.patyk.solarmaxx.backend.repository;

import cz.patyk.solarmaxx.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
