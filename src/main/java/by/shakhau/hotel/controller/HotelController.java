package by.shakhau.hotel.controller;

import by.shakhau.hotel.controller.exception.EntityNotFoundException;
import by.shakhau.hotel.controller.mapper.HotelControllerMapper;
import by.shakhau.hotel.controller.request.CreateHotelRequest;
import by.shakhau.hotel.controller.response.HotelResponse;
import by.shakhau.hotel.dto.Hotel;
import by.shakhau.hotel.model.HotelFiler;
import by.shakhau.hotel.service.HotelService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/property-view")
@AllArgsConstructor
public class HotelController {

    private HotelService hotelService;
    private HotelControllerMapper hotelControllerMapper;

    @GetMapping(value = "/hotels", produces = APPLICATION_JSON_VALUE)
    @ApiResponse(
            responseCode = "200",
            description = "Returns a list of all hotels."
    )
    public List<HotelResponse> getHotels() {
        return hotelService.findAll().stream()
                .map(h -> hotelControllerMapper.toResponse(h))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/hotels/{id}", produces = APPLICATION_JSON_VALUE)
    @ApiResponse(
            responseCode = "200",
            description = "Returns the hotel by id."
    )
    public Hotel getHotel(@PathVariable Long id) {
        return Optional.ofNullable(hotelService.findById(id))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Hotel with id = %s not found", id)));
    }

    @GetMapping(value = "/search", produces = APPLICATION_JSON_VALUE)
    @ApiResponse(
            responseCode = "200",
            description = "Returns a list of hotels by search parameters. Any parameter is not mandatory"
    )
    public List<HotelResponse> searchHotels(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) List<String> amenities) {
        return hotelService.search(new HotelFiler(name, brand, city, country, amenities)).stream()
                .map(h -> hotelControllerMapper.toResponse(h))
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/hotels", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiResponse(
            responseCode = "200",
            description = "Creates hotel info",
            content = @Content(
                    mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CreateHotelRequest.class)
            )
    )
    public HotelResponse createHotel(@RequestBody CreateHotelRequest request) {
        return Optional.ofNullable(hotelService.save(hotelControllerMapper.toDto(request)))
                .map(h -> hotelControllerMapper.toResponse(h))
                .orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping(value = "/hotels/{id}/amenities", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiResponse(
            responseCode = "200",
            description = "Adds amenities to the hotel info",
            content = @Content(
                    array = @ArraySchema(
                            schema = @Schema(implementation = String.class)
                    )
            )
    )
    public void addHotelAmenities(
            @Schema(accessMode = Schema.AccessMode.AUTO)
            @PathVariable Long id,
            @RequestBody List<String> amenities) {
        hotelService.addAmenities(id, amenities);
    }

    @GetMapping(value = "/histogram/{param}", produces = APPLICATION_JSON_VALUE)
    @ApiResponse(
            responseCode = "200",
            description = "Creates histogram with count of found param values like brand, city, country, amenities.",
            content = @Content(
                    schema = @Schema(implementation = String.class)
            )
    )
    public Map<String, Long> getHistogram(
            @Parameter(
                    description = "Parameter",
                    example = "amenities"
            )
            @PathVariable String param) {
        return hotelService.getHistogram(param);
    }
}
