package by.shakhau.hotel.dto;

import java.util.List;

public record HotelSearch(String name, String brand, String city, String country, List<String> amenities) {
}
