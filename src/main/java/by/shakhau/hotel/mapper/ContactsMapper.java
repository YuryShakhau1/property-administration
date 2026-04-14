package by.shakhau.hotel.mapper;

import by.shakhau.hotel.dto.Contacts;
import by.shakhau.hotel.model.HotelEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactsMapper {

    Contacts toDto(HotelEntity entity);
}
