package by.shakhau.hotel.controller.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class CreateAddressRequest {

    private Integer houseNumber;
    private String street;
    private String city;
    private String country;
    private String postCode;
}
