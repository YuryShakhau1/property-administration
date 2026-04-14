package by.shakhau.hotel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ArrivalTime {

    private LocalTime checkIn;
    private LocalTime checkOut;
}
