package by.shakhau.hotel.controller.response;

import by.shakhau.hotel.dto.ArrivalTime;
import by.shakhau.hotel.dto.Contacts;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class HotelResponse {

    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
}
