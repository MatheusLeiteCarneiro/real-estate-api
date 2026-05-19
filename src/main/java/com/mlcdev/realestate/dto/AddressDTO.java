package com.mlcdev.realestate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mlcdev.realestate.util.StateDeserializer;
import com.mlcdev.realestate.util.ZipCodeDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Address payload for a property.")
public class AddressDTO {

    @NotBlank(message = "This field should not be left blank")
    @Schema(description = "Street name.", example = "Main Street", requiredMode = Schema.RequiredMode.REQUIRED)
    private String street;

    @NotBlank(message = "This field should not be left blank")
    @Schema(description = "Street number.", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    private String number;

    @Schema(description = "Additional address information.", example = "Apartment 12")
    private String complement;

    @NotBlank(message = "This field should not be left blank")
    @Schema(description = "Neighborhood name.", example = "Downtown", requiredMode = Schema.RequiredMode.REQUIRED)
    private String neighborhood;

    @NotBlank(message = "This field should not be left blank")
    @Schema(description = "City name.", example = "Sample City", requiredMode = Schema.RequiredMode.REQUIRED)
    private String city;

    @NotNull(message = "This field should not be left null")
    @Size(min = 2, max = 2, message = "State must be a 2-character abbreviation")
    @JsonDeserialize(using = StateDeserializer.class)
    @Schema(description = "Two-letter state abbreviation.", example = "CA", minLength = 2, maxLength = 2, requiredMode = Schema.RequiredMode.REQUIRED)
    private String state;

    @NotNull(message = "This field should not be left null")
    @Size(min = 8, max = 8, message = "Unavailable ZipCode")
    @JsonDeserialize(using = ZipCodeDeserializer.class)
    @Schema(description = "Eight-digit ZIP code without separators.", example = "90000000", minLength = 8, maxLength = 8, requiredMode = Schema.RequiredMode.REQUIRED)
    private String zipCode;


}
