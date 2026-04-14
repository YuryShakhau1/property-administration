package by.shakhau.hotel.repository;

import by.shakhau.hotel.model.HotelEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<HotelEntity, Long> {

    @EntityGraph(attributePaths = {"address", "contacts", "arrivalTime", "amenities"})
    @Query("SELECT h FROM HotelEntity h WHERE h.id = :id")
    Optional<HotelEntity> findByIdFull(Long id);

    @EntityGraph(attributePaths = {"amenities"})
    @Query("SELECT h FROM HotelEntity h WHERE h.id = :id")
    Optional<HotelEntity> findByIdWithAmenities(Long id);
}
