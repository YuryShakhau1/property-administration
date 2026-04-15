package by.shakhau.hotel.repository.specification;

import by.shakhau.hotel.model.HotelEntity;
import by.shakhau.hotel.model.HotelFiler;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class HotelSpecification {

    public static Specification<HotelEntity> withFilters(HotelFiler filer) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (filer.name() != null && !filer.name().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("name")),
                        "%" + filer.name().toLowerCase() + "%"
                ));
            }

            if (filer.brand() != null && !filer.brand().isBlank()) {
                predicates.add(cb.equal(root.get("brand"), filer.brand()));
            }

            if (filer.city() != null && !filer.city().isBlank()) {
                Join<Object, Object> address = root.join("address", JoinType.INNER);
                predicates.add(cb.equal(address.get("city"), filer.city()));
            }

            if (filer.country() != null && !filer.country().isBlank()) {
                Join<Object, Object> address = root.join("address", JoinType.INNER);
                predicates.add(cb.equal(address.get("country"), filer.country()));
            }

            if (filer.amenities() != null && !filer.amenities().isEmpty()) {
                Join<Object, Object> amenities = root.join("amenities", JoinType.INNER);
                predicates.add(amenities.get("name").in(filer.amenities()));
                query.distinct(true);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
