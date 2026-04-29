package by.shakhau.hotel.service;

import by.shakhau.hotel.dto.Hotel;
import by.shakhau.hotel.model.HotelFiler;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface HotelService {

    List<Hotel> findAll();
    Hotel findById(Long id);
    Hotel save(Hotel hotel);
    void addAmenities(Long id, Collection<String> amenities);
    List<Hotel> search(HotelFiler hotelFiler);
    Map<String, Long> getHistogram(String param);
    void delete(Long id);
}
