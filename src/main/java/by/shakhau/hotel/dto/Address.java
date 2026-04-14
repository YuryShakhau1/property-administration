package by.shakhau.hotel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class Address {

    private Integer houseNumber;
    private String street;
    private String city;
    private String country;
    private String postCode;
}
