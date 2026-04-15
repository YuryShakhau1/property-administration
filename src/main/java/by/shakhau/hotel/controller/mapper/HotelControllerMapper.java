package by.shakhau.hotel.controller.mapper;

import by.shakhau.hotel.controller.request.CreateHotelRequest;
import by.shakhau.hotel.controller.response.HotelResponse;
import by.shakhau.hotel.dto.Address;
import by.shakhau.hotel.dto.Hotel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HotelControllerMapper {

    HotelResponse toResponse(Hotel dto);
    Hotel toDto(CreateHotelRequest request);

    default String mapAddressToString(Address address) {
        if (address == null) {
            return null;
        }

        return String.format("%s %s, %s, %s, %s",
                address.getHouseNumber(),
                address.getStreet(),
                address.getCity(),
                address.getPostCode(),
                address.getCountry()
        );
    }

    default Address mapStringToAddress(String addressStr) {
        if (addressStr == null || addressStr.isBlank()) {
            return null;
        }

        String[] parts = addressStr.split(",");

        Address address = new Address();
        address.setHouseNumber(Integer.parseInt(parts[0].trim()));
        address.setStreet(parts[1].trim());
        address.setCity(parts[2].trim());
        address.setPostCode(parts[3].trim());
        address.setCountry(parts[4].trim());

        return address;
    }
}
