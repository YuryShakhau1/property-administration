package by.shakhau.hotel.model;

import lombok.Builder;

import java.util.List;

@Builder
public record HotelFiler(String name, String brand, String city, String country, List<String> amenities) {
}
