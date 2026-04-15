package by.shakhau.hotel.controller.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class CreateContactsRequest {

    @Schema(example = "+375 17 309-80-00", description = "Phone number")
    private String phone;

    @Schema(example = "doubletreeminsk.info@hilton.com", description = "Email")
    private String email;
}
