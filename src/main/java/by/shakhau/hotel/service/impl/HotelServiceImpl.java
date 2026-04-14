package by.shakhau.hotel.service.impl;

import by.shakhau.hotel.dto.Hotel;
import by.shakhau.hotel.mapper.HotelMapper;
import by.shakhau.hotel.model.AmenityEntity;
import by.shakhau.hotel.model.HotelEntity;
import by.shakhau.hotel.repository.AmenityRepository;
import by.shakhau.hotel.repository.HotelRepository;
import by.shakhau.hotel.service.HotelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HotelServiceImpl implements HotelService {

    private HotelMapper hotelMapper;
    private HotelRepository hotelRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Hotel> findAll() {
        return hotelRepository.findAll().stream()
                .map(h -> hotelMapper.toDto(h))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Hotel findById(Long id) {
        return hotelMapper.toDtoFull(hotelRepository.findByIdFull(id).orElse(null));
    }

    @Transactional
    @Override
    public Hotel save(Hotel hotel) {
        HotelEntity hotelEntity = hotelMapper.toEntity(hotel);
        hotelEntity.setId(null);
        hotelEntity.getAddress().setHotel(hotelEntity);
        hotelEntity.getArrivalTime().setHotel(hotelEntity);
        hotelEntity.getContacts().setHotel(hotelEntity);
        hotelEntity.setAmenities(Collections.emptyList());
        return hotelMapper.toDtoFull(hotelRepository.save(hotelEntity));
    }
}
