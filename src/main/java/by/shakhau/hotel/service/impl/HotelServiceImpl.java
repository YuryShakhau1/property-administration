package by.shakhau.hotel.service.impl;

import by.shakhau.hotel.dto.Hotel;
import by.shakhau.hotel.mapper.HotelMapper;
import by.shakhau.hotel.model.AmenityEntity;
import by.shakhau.hotel.model.HotelFiler;
import by.shakhau.hotel.repository.AmenityRepository;
import by.shakhau.hotel.repository.HotelRepository;
import by.shakhau.hotel.repository.specification.HotelSpecification;
import by.shakhau.hotel.service.HotelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HotelServiceImpl implements HotelService {

    private HotelMapper hotelMapper;
    private HotelRepository hotelRepository;
    private AmenityRepository amenityRepository;

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
        var hotelEntity = hotelMapper.toEntity(hotel);
        hotelEntity.setId(null);
        hotelEntity.getAddress().setHotel(hotelEntity);
        hotelEntity.getArrivalTime().setHotel(hotelEntity);
        hotelEntity.getContacts().setHotel(hotelEntity);
        hotelEntity.setAmenities(Collections.emptyList());
        return hotelMapper.toDto(hotelRepository.save(hotelEntity));
    }

    @Transactional
    @Override
    public void addAmenities(Long id, Collection<String> amenities) {
        hotelRepository.findByIdFull(id).ifPresent(hotel -> {
            var existedAmenities = hotel.getAmenities().stream()
                    .map(AmenityEntity::getName)
                    .collect(Collectors.toSet());
            var amenitiesToAdd = new HashSet<>(amenities).stream()
                    .filter(amenityName -> !existedAmenities.contains(amenityName))
                    .map(amenityName -> amenityRepository.findByName(amenityName).orElseGet(() -> new AmenityEntity(amenityName)))
                    .toList();
            hotel.getAmenities().addAll(amenitiesToAdd);
            hotelRepository.save(hotel);
        });
    }

    @Override
    public List<Hotel> search(HotelFiler hotelFiler) {
        return hotelRepository.findAll(HotelSpecification.withFilters(hotelFiler)).stream()
                .map(h -> hotelMapper.toDto(h))
                .toList();
    }

    @Override
    public Map<String, Long> getHistogram(String param) {
        List<Object[]> rows;

        switch (param) {
            case "brand" -> rows = hotelRepository.countByBrand();
            case "city" -> rows = hotelRepository.countByCity();
            case "country" -> rows = hotelRepository.countByCountry();
            case "amenities" -> rows = hotelRepository.countByAmenities();
            default -> throw new IllegalArgumentException("Unknown param: " + param);
        }

        return rows.stream()
                .filter(r -> r[0] != null)
                .collect(Collectors.toMap(
                        r -> (String) r[0],
                        r -> (Long) r[1]
                ));
    }
}
