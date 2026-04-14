package by.shakhau.hotel.service;

import by.shakhau.hotel.dto.Hotel;

import java.util.List;

public interface HotelService {

    List<Hotel> findAll();
    Hotel findById(Long id);
    Hotel save(Hotel hotel);
}
