package by.shakhau.hotel.controller.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class CreateArrivalTimeRequest {

    @Schema(example = "14:00:00", description = "Check in time")
    private LocalTime checkIn;

    @Schema(example = "12:00:00", description = "Check out time")
    private LocalTime checkOut;
}
