package by.shakhau.hotel.controller.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class CreateHotelRequest {

    @Schema(example = "DoubleTree by Hilton Minsk", description = "Hotel name")
    private String name;

    @Schema(example = """
        The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belorussian capital and stunning \s
        views of Minsk city from the hotel's 20th floor ...""", description = "Description")
    private String description;

    @Schema(example = "Hilton", description = "Hotel brand")
    private String brand;

    private CreateAddressRequest address;
    private CreateContactsRequest contacts;
    private CreateArrivalTimeRequest arrivalTime;
    private List<String> amenities;
}
