package by.shakhau.hotel.controller;

import by.shakhau.hotel.dto.Hotel;
import by.shakhau.hotel.model.AmenityEntity;
import by.shakhau.hotel.model.HotelEntity;
import by.shakhau.hotel.service.HotelService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/property-view")
@AllArgsConstructor
public class HotelController {

    private HotelService hotelService;

    @GetMapping(value = "/hotels", produces = APPLICATION_JSON_VALUE)
    public List<Hotel> getHotels() {
        return hotelService.findAll();
    }

    @GetMapping(value = "/hotels/{id}", produces = APPLICATION_JSON_VALUE)
    public Hotel getHotel(@PathVariable long id) {
        return hotelService.findById(id);
    }

    @GetMapping(value = "/search", produces = APPLICATION_JSON_VALUE)
    public List<HotelEntity> searchHotels(@PathVariable long id) {
        return Arrays.asList();
    }

    @PostMapping(value = "/hotels", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public Hotel createHotel(@RequestBody Hotel hotel) {
        return hotelService.save(hotel);
    }

    @PostMapping(value = "/hotels/{id}/amenities", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void addHotelAmenities(@PathVariable List<AmenityEntity> amenities) {

    }

    @GetMapping(value = "/histogram/{param}", produces = APPLICATION_JSON_VALUE)
    public List<String> getHistogram(@PathVariable String param) {
        return Arrays.asList();
    }
}
