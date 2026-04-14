package by.shakhau.hotel.mapper;

import by.shakhau.hotel.dto.Address;
import by.shakhau.hotel.model.AddressEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address toDto(AddressEntity entity);
    AddressEntity toEntity(Address dto);
}
