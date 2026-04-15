package by.shakhau.hotel.controller.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class CreateAddressRequest {

    @Schema(example = "9", description = "House number")
    private Integer houseNumber;

    @Schema(example = "Pobediteley Avenue", description = "House number")
    private String street;

    @Schema(example = "Minsk", description = "City")
    private String city;

    @Schema(example = "Belarus", description = "Country")
    private String country;

    @Schema(example = "220004", description = "Postal code")
    private String postCode;
}
