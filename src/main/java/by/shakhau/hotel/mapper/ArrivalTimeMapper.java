package by.shakhau.hotel.mapper;

import by.shakhau.hotel.dto.ArrivalTime;
import by.shakhau.hotel.model.ArrivalTimeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArrivalTimeMapper {

    ArrivalTime toDto(ArrivalTimeEntity entity);
}
