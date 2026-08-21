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
    @Size(max = 255, message = "Street must not exceed 255 characters")
    @Schema(description = "Street name.", example = "Main Street", maxLength = 255, requiredMode = Schema.RequiredMode.REQUIRED)
    private String street;

    @NotBlank(message = "This field should not be left blank")
    @Size(max = 20, message = "Street number must not exceed 20 characters")
    @Schema(description = "Street number.", example = "100", maxLength = 20, requiredMode = Schema.RequiredMode.REQUIRED)
    private String number;

    @Size(max = 255, message = "Complement must not exceed 255 characters")
    @Schema(description = "Additional address information.", example = "Apartment 12", maxLength = 255)
    private String complement;

    @NotBlank(message = "This field should not be left blank")
    @Size(max = 255, message = "Neighborhood must not exceed 255 characters")
    @Schema(description = "Neighborhood name.", example = "Downtown", maxLength = 255, requiredMode = Schema.RequiredMode.REQUIRED)
    private String neighborhood;

    @NotBlank(message = "This field should not be left blank")
    @Size(max = 255, message = "City must not exceed 255 characters")
    @Schema(description = "City name.", example = "Sample City", maxLength = 255, requiredMode = Schema.RequiredMode.REQUIRED)
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
