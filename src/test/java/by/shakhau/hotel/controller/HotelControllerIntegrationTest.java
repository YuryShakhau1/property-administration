package by.shakhau.hotel.controller;

import by.shakhau.hotel.controller.response.HotelResponse;
import by.shakhau.hotel.dto.Address;
import by.shakhau.hotel.dto.ArrivalTime;
import by.shakhau.hotel.dto.Contacts;
import by.shakhau.hotel.dto.Hotel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

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
import static by.shakhau.hotel.util.TestUtil.createHotelRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class HotelControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldProcessFullHotelFlow() throws Exception {
        var hotelToSave = createHotelRequest();
        var amenitiesToSave = List.of(AMENITY1, AMENITY2);

        // Creates new hotel info
        HotelResponse saved = objectMapper.readValue(
                mockMvc.perform(post("/property-view/hotels")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(hotelToSave)))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(), HotelResponse.class);
        assertHotelResponse(saved);

        // Gets just created hotel info
        Hotel foundHotel = objectMapper.readValue(
                mockMvc.perform(get("/property-view/hotels/{id}", saved.getId()))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(), Hotel.class);
        assertFoundHotel(foundHotel);

        // Adds amenities to created hotel info
        mockMvc.perform(post("/property-view/hotels/{id}/amenities", saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(amenitiesToSave)))
                .andExpect(status().isOk());

        // Gets just created hotel info
        List<HotelResponse> foundHotels = objectMapper.readValue(
                mockMvc.perform(get("/property-view/search")
                                .param("amenities", AMENITY1))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(), new TypeReference<>() {});
        assertThat(foundHotels).isNotEmpty();
        assertHotelResponse(foundHotels.getFirst());

        // Gets histogram by amenities
        Map<String, Long> histogram = objectMapper.readValue(
                mockMvc.perform(get("/property-view/histogram/{param}", "amenities"))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(), new TypeReference<>() {});
        assertHistogram(histogram);

        // Gets all hotels
        List<HotelResponse> allHotels = objectMapper.readValue(
                mockMvc.perform(get("/property-view/hotels"))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(), new TypeReference<>() {});
        assertThat(allHotels).isNotEmpty();
        assertThat(allHotels).hasSize(1);
        assertHotelResponse(allHotels.getFirst());
    }

    private void assertHotelResponse(HotelResponse saved) {
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo(HOTEL_NAME);
        assertThat(saved.getDescription()).isEqualTo(HOTEL_DESCRIPTION);

        assertThat(saved.getAddress()).contains(String.valueOf(ADDRESS_HOUSE_NUMBER));
        assertThat(saved.getAddress()).contains(ADDRESS_STREET);
        assertThat(saved.getAddress()).contains((ADDRESS_CITY));
        assertThat(saved.getAddress()).contains(ADDRESS_COUNTRY);
        assertThat(saved.getAddress()).contains(ADDRESS_POST_CODE);
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
