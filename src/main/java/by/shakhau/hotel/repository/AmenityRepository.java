package by.shakhau.hotel.repository;

import by.shakhau.hotel.model.AmenityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AmenityRepository extends JpaRepository<AmenityEntity, Long> {

    Optional<AmenityEntity> findByName(String name);
}
