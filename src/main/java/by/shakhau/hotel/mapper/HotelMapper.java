package by.shakhau.hotel.mapper;

import by.shakhau.hotel.dto.Hotel;
import by.shakhau.hotel.model.AddressEntity;
import by.shakhau.hotel.model.AmenityEntity;
import by.shakhau.hotel.model.HotelEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { AddressMapper.class, ContactsMapper.class, ArrivalTimeMapper.class })
public interface HotelMapper {

    @Mapping(target = "contacts", ignore = true)
    @Mapping(target = "arrivalTime", ignore = true)
    @Mapping(target = "amenities", ignore = true)
    Hotel toDto(HotelEntity entity);

    Hotel toDtoFull(HotelEntity entity);

    HotelEntity toEntity(Hotel dto);

    default List<String> mapAmenitiesToNames(List<AmenityEntity> amenities) {
        if (amenities == null) return Collections.emptyList();
        return amenities.stream()
                .map(AmenityEntity::getName)
                .collect(Collectors.toList());
    }

    default List<AmenityEntity> mapNamesToAmenities(List<String> names) {
        if (names == null) return Collections.emptyList();
        return names.stream()
                .map(AmenityEntity::new)
                .collect(Collectors.toList());
    }
}
