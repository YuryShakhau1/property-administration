package by.shakhau.hotel.controller;

import by.shakhau.hotel.controller.response.HotelResponse;
import by.shakhau.hotel.controller.response.mapper.HotelResponseMapper;
import by.shakhau.hotel.dto.Hotel;
import by.shakhau.hotel.model.HotelFiler;
import by.shakhau.hotel.service.HotelService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/property-view")
@AllArgsConstructor
public class HotelController {

    private HotelService hotelService;
    private HotelResponseMapper hotelResponseMapper;

    @GetMapping(value = "/hotels", produces = APPLICATION_JSON_VALUE)
    public List<HotelResponse> getHotels() {
        return hotelService.findAll().stream()
                .map(h -> hotelResponseMapper.toResponse(h))
                .toList();
    }

    @GetMapping(value = "/hotels/{id}", produces = APPLICATION_JSON_VALUE)
    public Hotel getHotel(@PathVariable long id) {
        return hotelService.findById(id);
    }

    @GetMapping(value = "/search", produces = APPLICATION_JSON_VALUE)
    public List<Hotel> searchHotels(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) List<String> amenities) {
        return hotelService.search(new HotelFiler(name, brand, city, country, amenities));
    }

    @PostMapping(value = "/hotels", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public HotelResponse createHotel(@RequestBody Hotel hotel) {
        return Optional.ofNullable(hotelService.save(hotel))
                .map(h -> hotelResponseMapper.toResponse(h))
                .orElseThrow();
    }

    @PostMapping(value = "/hotels/{id}/amenities", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void addHotelAmenities(@PathVariable Long id, @RequestBody List<String> amenities) {
        hotelService.addAmenities(id, amenities);
    }

    @GetMapping(value = "/histogram/{param}", produces = APPLICATION_JSON_VALUE)
    public Map<String, Long> getHistogram(@PathVariable String param) {
        return hotelService.getHistogram(param);
    }
}
