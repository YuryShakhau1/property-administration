package by.shakhau.hotel.controller.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class CreateHotelRequest {

    private String name;
    private String description;
    private String brand;
    private CreateAddressRequest address;
    private CreateContactsRequest contacts;
    private CreateArrivalTimeRequest arrivalTime;
    private List<String> amenities;
}
