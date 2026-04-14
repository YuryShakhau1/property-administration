package by.shakhau.hotel.repository;

import by.shakhau.hotel.model.HotelEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<HotelEntity, Long>, JpaSpecificationExecutor<HotelEntity> {

    @EntityGraph(
            attributePaths = { "address", "contacts", "arrivalTime", "amenities" },
            type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT h FROM HotelEntity h WHERE h.id = :id")
    Optional<HotelEntity> findById(Long id);

    @Override
    @EntityGraph(
            attributePaths = { "address", "contacts", "arrivalTime", "amenities" },
            type = EntityGraph.EntityGraphType.FETCH)
    List<HotelEntity> findAll();

    @Query("select h.brand, count(h) from HotelEntity h group by h.brand")
    List<Object[]> countByBrand();

    @Query("select h.address.city, count(h) from HotelEntity h group by h.address.city")
    List<Object[]> countByCity();

    @Query("select h.address.country, count(h) from HotelEntity h group by h.address.country")
    List<Object[]> countByCountry();

    @Query("select a.name, count(h) from HotelEntity h join h.amenities a group by a.name")
    List<Object[]> countByAmenities();
}
