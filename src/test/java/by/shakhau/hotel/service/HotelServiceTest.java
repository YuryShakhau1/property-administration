package by.shakhau.hotel.service;

import by.shakhau.hotel.dto.Address;
import by.shakhau.hotel.dto.ArrivalTime;
import by.shakhau.hotel.dto.Contacts;
import by.shakhau.hotel.dto.Hotel;
import by.shakhau.hotel.mapper.HotelMapper;
import by.shakhau.hotel.model.AddressEntity;
import by.shakhau.hotel.model.AmenityEntity;
import by.shakhau.hotel.model.ArrivalTimeEntity;
import by.shakhau.hotel.model.ContactsEntity;
import by.shakhau.hotel.model.HotelEntity;
import by.shakhau.hotel.model.HotelFiler;
import by.shakhau.hotel.repository.AmenityRepository;
import by.shakhau.hotel.repository.HotelRepository;
import by.shakhau.hotel.service.impl.HotelServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HotelServiceTest {

    private static final Random r = new Random();
    private static final Long HOTEL_ID = r.nextLong();
    private static final String HOTEL_BRAND = UUID.randomUUID().toString();
    private static final String HOTEL_NAME = UUID.randomUUID().toString();
    private static final String HOTEL_DESCRIPTION = UUID.randomUUID().toString();

    private static final String AMENITY1 = UUID.randomUUID().toString();
    private static final String AMENITY2 = UUID.randomUUID().toString();
    private static final String AMENITY3 = UUID.randomUUID().toString();

    private static final Long ADDRESS_ID = r.nextLong();
    private static final String ADDRESS_CITY = UUID.randomUUID().toString();
    private static final String ADDRESS_COUNTRY = UUID.randomUUID().toString();
    private static final String ADDRESS_STREET = UUID.randomUUID().toString();
    private static final int ADDRESS_HOUSE_NUMBER = r.nextInt();
    private static final String ADDRESS_POST_CODE = String.valueOf(r.nextInt());

    private static final String CONTACTS_EMAIL = UUID.randomUUID().toString();
    private static final String CONTACTS_PHONE = String.valueOf(r.nextInt());

    private static final LocalTime CHECK_IN = LocalTime.of(1, 2, 3);
    private static final LocalTime CHECK_OUT = LocalTime.of(4, 5, 6);

    private static final String HISTOGRAM_VALUE_1 = UUID.randomUUID().toString();
    private static final String HISTOGRAM_VALUE_2 = UUID.randomUUID().toString();

    private static final Long HISTOGRAM_COUNT_1 = r.nextLong();
    private static final Long HISTOGRAM_COUNT_2 = r.nextLong();

    @Mock
    private HotelMapper hotelMapper;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private AmenityRepository amenityRepository;

    @InjectMocks
    private HotelServiceImpl service;

    @Test
    public void shouldReturnAllHotels() {
        HotelEntity hotel = createHotelEntity();

        when(hotelMapper.toDto(hotel)).thenReturn(createHotel());
        when(hotelRepository.findAll()).thenReturn(List.of(hotel));

        List<Hotel> foundHotels = service.findAll();

        assertThat(foundHotels).isNotNull();
        assertThat(foundHotels.size()).isEqualTo(1);

        Hotel foundHotel = foundHotels.getFirst();
        assertThat(foundHotel).isNotNull();
        assertThat(foundHotel.getId()).isEqualTo(HOTEL_ID);
        assertThat(foundHotel.getName()).isEqualTo(hotel.getName());
        assertThat(foundHotel.getBrand()).isEqualTo(hotel.getBrand());
        assertThat(foundHotel.getDescription()).isEqualTo(hotel.getDescription());

        Address foundAddress = foundHotel.getAddress();
        assertThat(foundAddress).isNotNull();
        assertThat(foundAddress.getCity()).isEqualTo(hotel.getAddress().getCity());
        assertThat(foundAddress.getCountry()).isEqualTo(hotel.getAddress().getCountry());
        assertThat(foundAddress.getStreet()).isEqualTo(hotel.getAddress().getStreet());
        assertThat(foundAddress.getHouseNumber()).isEqualTo(hotel.getAddress().getHouseNumber());
        assertThat(foundAddress.getPostCode()).isEqualTo(hotel.getAddress().getPostCode());

        Contacts foundContacts = foundHotel.getContacts();
        assertThat(foundContacts.getEmail()).isEqualTo(hotel.getContacts().getEmail());
        assertThat(foundContacts.getPhone()).isEqualTo(hotel.getContacts().getPhone());

        ArrivalTime foundArrivalTime = foundHotel.getArrivalTime();
        assertThat(foundArrivalTime.getCheckIn()).isEqualTo(hotel.getArrivalTime().getCheckIn());
        assertThat(foundArrivalTime.getCheckOut()).isEqualTo(hotel.getArrivalTime().getCheckOut());

        List<String> foundAmenities = foundHotel.getAmenities();
        assertThat(foundAmenities).isNotNull();
        assertThat(foundAmenities.size()).isEqualTo(hotel.getAmenities().size());

        assertThat(foundAmenities.get(0)).isEqualTo(AMENITY1);
        assertThat(foundAmenities.get(1)).isEqualTo(AMENITY2);
        assertThat(foundAmenities.get(2)).isEqualTo(AMENITY3);
    }

    @Test
    public void shouldReturnHotelByIdWhenExists() {
        HotelEntity hotel = createHotelEntity();

        when(hotelMapper.toDtoFull(hotel)).thenReturn(createHotel());
        when(hotelRepository.findById(HOTEL_ID)).thenReturn(Optional.of(hotel));

        Hotel foundHotel = service.findById(HOTEL_ID);

        assertThat(foundHotel).isNotNull();
        assertThat(foundHotel.getId()).isEqualTo(HOTEL_ID);
        assertThat(foundHotel.getName()).isEqualTo(hotel.getName());
        assertThat(foundHotel.getBrand()).isEqualTo(hotel.getBrand());
        assertThat(foundHotel.getDescription()).isEqualTo(hotel.getDescription());

        Address foundAddress = foundHotel.getAddress();
        assertThat(foundAddress).isNotNull();
        assertThat(foundAddress.getCity()).isEqualTo(hotel.getAddress().getCity());
        assertThat(foundAddress.getCountry()).isEqualTo(hotel.getAddress().getCountry());
        assertThat(foundAddress.getStreet()).isEqualTo(hotel.getAddress().getStreet());
        assertThat(foundAddress.getHouseNumber()).isEqualTo(hotel.getAddress().getHouseNumber());
        assertThat(foundAddress.getPostCode()).isEqualTo(hotel.getAddress().getPostCode());

        Contacts foundContacts = foundHotel.getContacts();
        assertThat(foundContacts.getEmail()).isEqualTo(hotel.getContacts().getEmail());
        assertThat(foundContacts.getPhone()).isEqualTo(hotel.getContacts().getPhone());

        ArrivalTime foundArrivalTime = foundHotel.getArrivalTime();
        assertThat(foundArrivalTime.getCheckIn()).isEqualTo(hotel.getArrivalTime().getCheckIn());
        assertThat(foundArrivalTime.getCheckOut()).isEqualTo(hotel.getArrivalTime().getCheckOut());

        List<String> foundAmenities = foundHotel.getAmenities();
        assertThat(foundAmenities).isNotNull();
        assertThat(foundAmenities.size()).isEqualTo(hotel.getAmenities().size());

        assertThat(foundAmenities.get(0)).isEqualTo(AMENITY1);
        assertThat(foundAmenities.get(1)).isEqualTo(AMENITY2);
        assertThat(foundAmenities.get(2)).isEqualTo(AMENITY3);
    }

    @Test
    public void shouldReturnHotelByIdWhenDoesNotExist() {
        when(hotelRepository.findById(HOTEL_ID)).thenReturn(Optional.empty());

        Hotel foundHotel = service.findById(HOTEL_ID);
        assertThat(foundHotel).isNull();
    }

    @Test
    public void shouldSaveHotel() {
        Hotel hotel = createHotel();
        HotelEntity hotelEntity = createHotelEntity();

        when(hotelMapper.toEntity(hotel)).thenReturn(hotelEntity);
        when(hotelMapper.toDto(hotelEntity)).thenReturn(hotel);
        when(hotelRepository.save(hotelEntity)).thenReturn(hotelEntity);

        Hotel foundHotel = service.save(hotel);

        assertThat(foundHotel).isNotNull();
        assertThat(foundHotel.getId()).isEqualTo(HOTEL_ID);
        assertThat(foundHotel.getName()).isEqualTo(hotel.getName());
        assertThat(foundHotel.getBrand()).isEqualTo(hotel.getBrand());
        assertThat(foundHotel.getDescription()).isEqualTo(hotel.getDescription());

        Address foundAddress = foundHotel.getAddress();
        assertThat(foundAddress).isNotNull();
        assertThat(foundAddress.getCity()).isEqualTo(hotel.getAddress().getCity());
        assertThat(foundAddress.getCountry()).isEqualTo(hotel.getAddress().getCountry());
        assertThat(foundAddress.getStreet()).isEqualTo(hotel.getAddress().getStreet());
        assertThat(foundAddress.getHouseNumber()).isEqualTo(hotel.getAddress().getHouseNumber());
        assertThat(foundAddress.getPostCode()).isEqualTo(hotel.getAddress().getPostCode());

        Contacts foundContacts = foundHotel.getContacts();
        assertThat(foundContacts.getEmail()).isEqualTo(hotel.getContacts().getEmail());
        assertThat(foundContacts.getPhone()).isEqualTo(hotel.getContacts().getPhone());

        ArrivalTime foundArrivalTime = foundHotel.getArrivalTime();
        assertThat(foundArrivalTime.getCheckIn()).isEqualTo(hotel.getArrivalTime().getCheckIn());
        assertThat(foundArrivalTime.getCheckOut()).isEqualTo(hotel.getArrivalTime().getCheckOut());

        List<String> foundAmenities = foundHotel.getAmenities();
        assertThat(foundAmenities).isNotNull();
        assertThat(foundAmenities.size()).isEqualTo(hotel.getAmenities().size());

        assertThat(foundAmenities.get(0)).isEqualTo(AMENITY1);
        assertThat(foundAmenities.get(1)).isEqualTo(AMENITY2);
        assertThat(foundAmenities.get(2)).isEqualTo(AMENITY3);
    }

    @Test
    public void shouldReturnListOfHotelsByHotelNameAndCity() {
        var filer = new HotelFiler(HOTEL_NAME, null, ADDRESS_CITY, null, null);
        var hotel = createHotel();
        var hotelEntity = createHotelEntity();

        when(hotelMapper.toDto(hotelEntity)).thenReturn(hotel);
        when(hotelRepository.findAll(any(Specification.class))).thenReturn(List.of(hotelEntity));

        List<Hotel> foundHotels = service.search(filer);

        Hotel foundHotel = foundHotels.getFirst();
        assertThat(foundHotel).isNotNull();
        assertThat(foundHotel.getId()).isEqualTo(HOTEL_ID);
        assertThat(foundHotel.getName()).isEqualTo(hotel.getName());
        assertThat(foundHotel.getBrand()).isEqualTo(hotel.getBrand());
        assertThat(foundHotel.getDescription()).isEqualTo(hotel.getDescription());

        Address foundAddress = foundHotel.getAddress();
        assertThat(foundAddress).isNotNull();
        assertThat(foundAddress.getCity()).isEqualTo(hotel.getAddress().getCity());
        assertThat(foundAddress.getCountry()).isEqualTo(hotel.getAddress().getCountry());
        assertThat(foundAddress.getStreet()).isEqualTo(hotel.getAddress().getStreet());
        assertThat(foundAddress.getHouseNumber()).isEqualTo(hotel.getAddress().getHouseNumber());
        assertThat(foundAddress.getPostCode()).isEqualTo(hotel.getAddress().getPostCode());

        Contacts foundContacts = foundHotel.getContacts();
        assertThat(foundContacts.getEmail()).isEqualTo(hotel.getContacts().getEmail());
        assertThat(foundContacts.getPhone()).isEqualTo(hotel.getContacts().getPhone());

        ArrivalTime foundArrivalTime = foundHotel.getArrivalTime();
        assertThat(foundArrivalTime.getCheckIn()).isEqualTo(hotel.getArrivalTime().getCheckIn());
        assertThat(foundArrivalTime.getCheckOut()).isEqualTo(hotel.getArrivalTime().getCheckOut());

        List<String> foundAmenities = foundHotel.getAmenities();
        assertThat(foundAmenities).isNotNull();
        assertThat(foundAmenities.size()).isEqualTo(hotel.getAmenities().size());

        assertThat(foundAmenities.get(0)).isEqualTo(AMENITY1);
        assertThat(foundAmenities.get(1)).isEqualTo(AMENITY2);
        assertThat(foundAmenities.get(2)).isEqualTo(AMENITY3);
    }

    @Test
    public void shouldAddAmenitiesWhenDoesNotContainInHotelOnly() {
        var amenityToAdd = UUID.randomUUID().toString();
        var existedAmenityToAdd = AMENITY2;
        var hotelEntity = createHotelEntity();

        assertThat(hotelEntity.getAmenities().size()).isEqualTo(3);

        when(hotelRepository.findById(HOTEL_ID)).thenReturn(Optional.of(hotelEntity));
        when(hotelRepository.save(hotelEntity)).thenReturn(hotelEntity);

        service.addAmenities(HOTEL_ID, List.of(amenityToAdd, existedAmenityToAdd));

        List<AmenityEntity> amenities = hotelEntity.getAmenities();
        assertThat(amenities.size()).isEqualTo(4);
        assertThat(amenities.get(0).getName()).isEqualTo(AMENITY1);
        assertThat(amenities.get(1).getName()).isEqualTo(AMENITY2);
        assertThat(amenities.get(2).getName()).isEqualTo(AMENITY3);
        assertThat(amenities.get(3).getName()).isEqualTo(amenityToAdd);
    }

    @Test
    public void shouldReturnEmptyListWhenNotFound() {
        var filer = new HotelFiler(HOTEL_NAME, null, ADDRESS_CITY, null, null);
        when(hotelRepository.findAll(any(Specification.class))).thenReturn(List.of());

        List<Hotel> foundHotels = service.search(filer);

        assertThat(foundHotels).isEmpty();
    }

    @Test
    public void shouldReturnHistogramByBrand() {
        var param = "brand";
        List<Object[]> histogramValues = createHistogram();

        when(hotelRepository.countByBrand()).thenReturn(histogramValues);

        Map<String, Long> histogram = service.getHistogram(param);

        verify(hotelRepository, never()).countByCity();
        verify(hotelRepository, never()).countByCountry();
        verify(hotelRepository, never()).countByAmenities();

        assertThat(histogram).isNotNull();
        assertThat(histogram.size()).isEqualTo(2);
        assertThat(histogram.get(HISTOGRAM_VALUE_1)).isEqualTo(HISTOGRAM_COUNT_1);
        assertThat(histogram.get(HISTOGRAM_VALUE_2)).isEqualTo(HISTOGRAM_COUNT_2);
    }

    @Test
    public void shouldReturnHistogramByCity() {
        var param = "city";
        List<Object[]> histogramValues = createHistogram();

        when(hotelRepository.countByCity()).thenReturn(histogramValues);

        Map<String, Long> histogram = service.getHistogram(param);

        verify(hotelRepository, never()).countByBrand();
        verify(hotelRepository, never()).countByCountry();
        verify(hotelRepository, never()).countByAmenities();

        assertThat(histogram).isNotNull();
        assertThat(histogram.size()).isEqualTo(2);
        assertThat(histogram.get(HISTOGRAM_VALUE_1)).isEqualTo(HISTOGRAM_COUNT_1);
        assertThat(histogram.get(HISTOGRAM_VALUE_2)).isEqualTo(HISTOGRAM_COUNT_2);
    }

    @Test
    public void shouldReturnHistogramByCountry() {
        var param = "country";
        List<Object[]> histogramValues = createHistogram();

        when(hotelRepository.countByCountry()).thenReturn(histogramValues);

        Map<String, Long> histogram = service.getHistogram(param);

        verify(hotelRepository, never()).countByBrand();
        verify(hotelRepository, never()).countByCity();
        verify(hotelRepository, never()).countByAmenities();

        assertThat(histogram).isNotNull();
        assertThat(histogram.size()).isEqualTo(2);
        assertThat(histogram.get(HISTOGRAM_VALUE_1)).isEqualTo(HISTOGRAM_COUNT_1);
        assertThat(histogram.get(HISTOGRAM_VALUE_2)).isEqualTo(HISTOGRAM_COUNT_2);
    }

    @Test
    public void shouldReturnHistogramByAmenities() {
        var param = "amenities";

        when(hotelRepository.countByAmenities()).thenReturn(createHistogram());

        Map<String, Long> histogram = service.getHistogram(param);

        verify(hotelRepository, never()).countByBrand();
        verify(hotelRepository, never()).countByCity();
        verify(hotelRepository, never()).countByCountry();

        assertThat(histogram).isNotNull();
        assertThat(histogram.size()).isEqualTo(2);
        assertThat(histogram.get(HISTOGRAM_VALUE_1)).isEqualTo(HISTOGRAM_COUNT_1);
        assertThat(histogram.get(HISTOGRAM_VALUE_2)).isEqualTo(HISTOGRAM_COUNT_2);
    }

    @Test
    public void shouldThrowExceptionWhenParameterIsUnknown() {
        var unknownParam = UUID.randomUUID().toString();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.getHistogram(unknownParam);
        });

        verify(hotelRepository, never()).countByBrand();
        verify(hotelRepository, never()).countByCity();
        verify(hotelRepository, never()).countByCountry();
        verify(hotelRepository, never()).countByAmenities();
    }

    private HotelEntity createHotelEntity() {
        var hotel = new HotelEntity();
        hotel.setId(HOTEL_ID);
        hotel.setBrand(HOTEL_BRAND);
        hotel.setName(HOTEL_NAME);
        hotel.setDescription(HOTEL_DESCRIPTION);
        hotel.setAmenities(new ArrayList<>(List.of(
                new AmenityEntity(AMENITY1),
                new AmenityEntity(AMENITY2),
                new AmenityEntity(AMENITY3))));
        hotel.setAddress(createAddressEntity(hotel));
        hotel.setContacts(createContactsEntity(hotel));
        hotel.setArrivalTime(createArrivalTimeEntity());
        return hotel;
    }

    private AddressEntity createAddressEntity(HotelEntity hotel) {
        var address = new AddressEntity();
        address.setId(ADDRESS_ID);
        address.setCity(ADDRESS_CITY);
        address.setCountry(ADDRESS_COUNTRY);
        address.setStreet(ADDRESS_STREET);
        address.setHouseNumber(ADDRESS_HOUSE_NUMBER);
        address.setPostCode(ADDRESS_POST_CODE);
        address.setHotel(hotel);
        return address;
    }

    private ContactsEntity createContactsEntity(HotelEntity hotel) {
        var contacts = new ContactsEntity();
        contacts.setEmail(CONTACTS_EMAIL);
        contacts.setPhone(CONTACTS_PHONE);
        contacts.setHotel(hotel);
        return contacts;
    }

    private ArrivalTimeEntity createArrivalTimeEntity() {
        var arrivalTime = new ArrivalTimeEntity();
        arrivalTime.setCheckIn(CHECK_IN);
        arrivalTime.setCheckOut(CHECK_OUT);
        return arrivalTime;
    }

    private Hotel createHotel() {
        var hotel = new Hotel();
        hotel.setId(HOTEL_ID);
        hotel.setBrand(HOTEL_BRAND);
        hotel.setName(HOTEL_NAME);
        hotel.setDescription(HOTEL_DESCRIPTION);
        hotel.setAmenities(new ArrayList<>(List.of(
                AMENITY1, AMENITY2, AMENITY3)));
        hotel.setAddress(createAddress());
        hotel.setContacts(createContacts());
        hotel.setArrivalTime(createArrivalTime());
        return hotel;
    }

    private Address createAddress() {
        var address = new Address();
        address.setCity(ADDRESS_CITY);
        address.setCountry(ADDRESS_COUNTRY);
        address.setStreet(ADDRESS_STREET);
        address.setHouseNumber(ADDRESS_HOUSE_NUMBER);
        address.setPostCode(ADDRESS_POST_CODE);
        return address;
    }

    private ArrivalTime createArrivalTime() {
        var arrivalTime = new ArrivalTime();
        arrivalTime.setCheckIn(CHECK_IN);
        arrivalTime.setCheckOut(CHECK_OUT);
        return arrivalTime;
    }

    private Contacts createContacts() {
        var contacts = new Contacts();
        contacts.setEmail(CONTACTS_EMAIL);
        contacts.setPhone(CONTACTS_PHONE);
        return contacts;
    }

    private List<Object[]> createHistogram() {
        return List.of(
                new Object[] { HISTOGRAM_VALUE_1, HISTOGRAM_COUNT_1 },
                new Object[] { HISTOGRAM_VALUE_2, HISTOGRAM_COUNT_2 }
        );
    }
}
