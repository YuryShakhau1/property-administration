package by.shakhau.hotel.util;

import by.shakhau.hotel.controller.request.CreateAddressRequest;
import by.shakhau.hotel.controller.request.CreateArrivalTimeRequest;
import by.shakhau.hotel.controller.request.CreateContactsRequest;
import by.shakhau.hotel.controller.request.CreateHotelRequest;
import by.shakhau.hotel.dto.Address;
import by.shakhau.hotel.dto.ArrivalTime;
import by.shakhau.hotel.dto.Contacts;
import by.shakhau.hotel.dto.Hotel;
import by.shakhau.hotel.model.AddressEntity;
import by.shakhau.hotel.model.AmenityEntity;
import by.shakhau.hotel.model.ArrivalTimeEntity;
import by.shakhau.hotel.model.ContactsEntity;
import by.shakhau.hotel.model.HotelEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestUtil {

    public static final Random r = new Random();
    public static final Long HOTEL_ID = r.nextLong();
    public static final String HOTEL_BRAND = UUID.randomUUID().toString();
    public static final String HOTEL_NAME = UUID.randomUUID().toString();
    public static final String HOTEL_DESCRIPTION = UUID.randomUUID().toString();

    public static final String AMENITY1 = UUID.randomUUID().toString();
    public static final String AMENITY2 = UUID.randomUUID().toString();
    public static final String AMENITY3 = UUID.randomUUID().toString();

    public static final Long ADDRESS_ID = r.nextLong();
    public static final String ADDRESS_CITY = UUID.randomUUID().toString();
    public static final String ADDRESS_COUNTRY = UUID.randomUUID().toString();
    public static final String ADDRESS_STREET = UUID.randomUUID().toString();
    public static final int ADDRESS_HOUSE_NUMBER = r.nextInt();
    public static final String ADDRESS_POST_CODE = String.valueOf(r.nextInt());

    public static final String CONTACTS_EMAIL = UUID.randomUUID().toString();
    public static final String CONTACTS_PHONE = String.valueOf(r.nextInt());

    public static final LocalTime CHECK_IN = LocalTime.of(1, 2, 3);
    public static final LocalTime CHECK_OUT = LocalTime.of(4, 5, 6);

    public static final String HISTOGRAM_VALUE_1 = UUID.randomUUID().toString();
    public static final String HISTOGRAM_VALUE_2 = UUID.randomUUID().toString();

    public static final Long HISTOGRAM_COUNT_1 = r.nextLong();
    public static final Long HISTOGRAM_COUNT_2 = r.nextLong();

    public static HotelEntity createHotelEntity() {
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

    public static AddressEntity createAddressEntity(HotelEntity hotel) {
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

    public static ContactsEntity createContactsEntity(HotelEntity hotel) {
        var contacts = new ContactsEntity();
        contacts.setEmail(CONTACTS_EMAIL);
        contacts.setPhone(CONTACTS_PHONE);
        contacts.setHotel(hotel);
        return contacts;
    }

    public static ArrivalTimeEntity createArrivalTimeEntity() {
        var arrivalTime = new ArrivalTimeEntity();
        arrivalTime.setCheckIn(CHECK_IN);
        arrivalTime.setCheckOut(CHECK_OUT);
        return arrivalTime;
    }

    public static Hotel createHotel() {
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

    public static Address createAddress() {
        var address = new Address();
        address.setCity(ADDRESS_CITY);
        address.setCountry(ADDRESS_COUNTRY);
        address.setStreet(ADDRESS_STREET);
        address.setHouseNumber(ADDRESS_HOUSE_NUMBER);
        address.setPostCode(ADDRESS_POST_CODE);
        return address;
    }

    public static ArrivalTime createArrivalTime() {
        var arrivalTime = new ArrivalTime();
        arrivalTime.setCheckIn(CHECK_IN);
        arrivalTime.setCheckOut(CHECK_OUT);
        return arrivalTime;
    }

    public static Contacts createContacts() {
        var contacts = new Contacts();
        contacts.setEmail(CONTACTS_EMAIL);
        contacts.setPhone(CONTACTS_PHONE);
        return contacts;
    }

    public static CreateHotelRequest createHotelRequest() {
        var hotel = new CreateHotelRequest();
        hotel.setBrand(HOTEL_BRAND);
        hotel.setName(HOTEL_NAME);
        hotel.setDescription(HOTEL_DESCRIPTION);
        hotel.setAddress(createAddressRequest());
        hotel.setContacts(createContactsRequest());
        hotel.setArrivalTime(createArrivalTimeRequest());
        return hotel;
    }

    public static CreateAddressRequest createAddressRequest() {
        var address = new CreateAddressRequest();
        address.setCity(ADDRESS_CITY);
        address.setCountry(ADDRESS_COUNTRY);
        address.setStreet(ADDRESS_STREET);
        address.setHouseNumber(ADDRESS_HOUSE_NUMBER);
        address.setPostCode(ADDRESS_POST_CODE);
        return address;
    }

    public static CreateArrivalTimeRequest createArrivalTimeRequest() {
        var arrivalTime = new CreateArrivalTimeRequest();
        arrivalTime.setCheckIn(CHECK_IN);
        arrivalTime.setCheckOut(CHECK_OUT);
        return arrivalTime;
    }

    public static CreateContactsRequest createContactsRequest() {
        var contacts = new CreateContactsRequest();
        contacts.setEmail(CONTACTS_EMAIL);
        contacts.setPhone(CONTACTS_PHONE);
        return contacts;
    }

    public static List<Object[]> createHistogram() {
        return List.of(
                new Object[] { HISTOGRAM_VALUE_1, HISTOGRAM_COUNT_1 },
                new Object[] { HISTOGRAM_VALUE_2, HISTOGRAM_COUNT_2 }
        );
    }
}
