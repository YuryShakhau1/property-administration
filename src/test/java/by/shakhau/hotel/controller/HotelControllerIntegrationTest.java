package by.shakhau.hotel.controller;

import by.shakhau.hotel.controller.response.HotelResponse;
import by.shakhau.hotel.dto.Address;
import by.shakhau.hotel.dto.ArrivalTime;
import by.shakhau.hotel.dto.Contacts;
import by.shakhau.hotel.dto.Hotel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.shakhau.hotel.util.TestUtil.ADDRESS_CITY;
import static by.shakhau.hotel.util.TestUtil.ADDRESS_COUNTRY;
import static by.shakhau.hotel.util.TestUtil.ADDRESS_HOUSE_NUMBER;
import static by.shakhau.hotel.util.TestUtil.ADDRESS_POST_CODE;
import static by.shakhau.hotel.util.TestUtil.ADDRESS_STREET;
import static by.shakhau.hotel.util.TestUtil.AMENITY1;
import static by.shakhau.hotel.util.TestUtil.AMENITY2;
import static by.shakhau.hotel.util.TestUtil.AMENITY3;
import static by.shakhau.hotel.util.TestUtil.CHECK_IN;
import static by.shakhau.hotel.util.TestUtil.CHECK_OUT;
import static by.shakhau.hotel.util.TestUtil.CONTACTS_EMAIL;
import static by.shakhau.hotel.util.TestUtil.CONTACTS_PHONE;
import static by.shakhau.hotel.util.TestUtil.HOTEL_BRAND;
import static by.shakhau.hotel.util.TestUtil.HOTEL_DESCRIPTION;
import static by.shakhau.hotel.util.TestUtil.HOTEL_NAME;
import static by.shakhau.hotel.util.TestUtil.createHotel;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class HotelControllerIntegrationTest {

    @Value("${server.port}")
    private Integer port;
    private WebTestClient webClient;

    @BeforeEach
    public void setup() {
        webClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    public void shouldProcessFullHotelFlow() {
        var hotelToSave = createHotel();
        hotelToSave.setId(null);
        var amenitiesToSave = List.of(AMENITY1, AMENITY2);

        // Creates new hotel info
        HotelResponse saved = webClient.post().uri("/property-view/hotels")
                .headers(headers -> headers.setAccept(List.of(MediaType.APPLICATION_JSON)))
                .body(BodyInserters.fromValue(hotelToSave))
                .exchange()
                .expectStatus().isOk()
                .expectBody(HotelResponse.class)
                .returnResult().getResponseBody();

        assertHotelResponse(saved);

        // Gets just created hotel info
        Hotel foundHotel = webClient.get().uri("/property-view/hotels/{id}", saved.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Hotel.class)
                .returnResult().getResponseBody();

        assertFoundHotel(foundHotel);

        // Adds amenities to created hotel info
        webClient.post().uri("/property-view/hotels/{id}/amenities", saved.getId())
                .headers(headers -> headers.setAccept(List.of(MediaType.APPLICATION_JSON)))
                .body(BodyInserters.fromValue(amenitiesToSave))
                .exchange()
                .expectStatus().isOk();

        // Gets just created hotel info
        List<HotelResponse> foundHotels = webClient.get().uri(uriBuilder -> uriBuilder
                        .path("/property-view/search")
                        .queryParam("amenities", AMENITY1)
                        .queryParamIfPresent("stars", Optional.of(5)) // если параметр опциональный
                        .build()
                )
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(HotelResponse.class)
                .returnResult().getResponseBody();

        assertThat(foundHotels).isNotEmpty();
        assertHotelResponse(foundHotels.getFirst());

        // Gets histogram by amenities
        Map<String, Long> histogram = webClient.get().uri("/property-view/histogram/{param}", "amenities")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Map<String, Long>>() {})
                .returnResult().getResponseBody();

        assertHistogram(histogram);

        // Gets all hotels
        List<HotelResponse> allHotels = webClient.get().uri("/property-view/hotels")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(HotelResponse.class)
                .returnResult().getResponseBody();

        assertThat(allHotels).isNotEmpty();
        assertThat(allHotels).hasSize(1);
        assertHotelResponse(allHotels.getFirst());
    }

    private void assertHotelResponse(HotelResponse saved) {
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo(HOTEL_NAME);
        assertThat(saved.getBrand()).isEqualTo(HOTEL_BRAND);
        assertThat(saved.getDescription()).isEqualTo(HOTEL_DESCRIPTION);

        assertThat(saved.getAddress()).contains(String.valueOf(ADDRESS_HOUSE_NUMBER));
        assertThat(saved.getAddress()).contains(ADDRESS_STREET);
        assertThat(saved.getAddress()).contains((ADDRESS_CITY));
        assertThat(saved.getAddress()).contains(ADDRESS_COUNTRY);
        assertThat(saved.getAddress()).contains(ADDRESS_POST_CODE);

        assertThat(saved.getContacts()).isNull();
        assertThat(saved.getArrivalTime()).isNull();
        assertThat(saved.getAmenities()).isNull();
    }

    private void assertFoundHotel(Hotel foundHotel) {
        assertThat(foundHotel).isNotNull();
        assertThat(foundHotel.getId()).isNotNull();
        assertThat(foundHotel.getName()).isEqualTo(HOTEL_NAME);
        assertThat(foundHotel.getBrand()).isEqualTo(HOTEL_BRAND);
        assertThat(foundHotel.getDescription()).isEqualTo(HOTEL_DESCRIPTION);

        Address foundAddress = foundHotel.getAddress();
        assertThat(foundAddress).isNotNull();
        assertThat(foundAddress.getCity()).isEqualTo(ADDRESS_CITY);
        assertThat(foundAddress.getCountry()).isEqualTo(ADDRESS_COUNTRY);
        assertThat(foundAddress.getStreet()).isEqualTo(ADDRESS_STREET);
        assertThat(foundAddress.getHouseNumber()).isEqualTo(ADDRESS_HOUSE_NUMBER);
        assertThat(foundAddress.getPostCode()).isEqualTo(ADDRESS_POST_CODE);

        Contacts foundContacts = foundHotel.getContacts();
        assertThat(foundContacts.getEmail()).isEqualTo(CONTACTS_EMAIL);
        assertThat(foundContacts.getPhone()).isEqualTo(CONTACTS_PHONE);

        ArrivalTime foundArrivalTime = foundHotel.getArrivalTime();
        assertThat(foundArrivalTime.getCheckIn()).isEqualTo(CHECK_IN);
        assertThat(foundArrivalTime.getCheckOut()).isEqualTo(CHECK_OUT);

        List<String> foundAmenities = foundHotel.getAmenities();
        assertThat(foundAmenities).isNotNull();
        assertThat(foundAmenities).isEmpty();
    }

    private void assertHistogram(Map<String, Long> histogram) {
        assertThat(histogram).isNotNull();
        assertThat(histogram.size()).isEqualTo(2);
        assertThat(histogram.get(AMENITY1)).isEqualTo(1);
        assertThat(histogram.get(AMENITY2)).isEqualTo(1);
        assertThat(histogram.get(AMENITY3)).isNull();
    }
}
