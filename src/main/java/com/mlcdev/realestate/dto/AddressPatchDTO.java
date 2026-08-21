package com.mlcdev.realestate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mlcdev.realestate.util.StateDeserializer;
import com.mlcdev.realestate.util.ZipCodeDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Partial address payload. Only provided fields are updated.")
public class AddressPatchDTO {

    @Size(max = 255, message = "Street must not exceed 255 characters")
    @Schema(description = "New street name.", example = "Updated Main Street", maxLength = 255)
    private String street;

    @Size(max = 20, message = "Street number must not exceed 20 characters")
    @Schema(description = "New street number.", example = "200", maxLength = 20)
    private String number;

    @Size(max = 255, message = "Complement must not exceed 255 characters")
    @Schema(description = "New additional address information.", example = "Suite 5", maxLength = 255)
    private String complement;

    @Size(max = 255, message = "Neighborhood must not exceed 255 characters")
    @Schema(description = "New neighborhood name.", example = "Central District", maxLength = 255)
    private String neighborhood;

    @Size(max = 255, message = "City must not exceed 255 characters")
    @Schema(description = "New city name.", example = "Updated City", maxLength = 255)
    private String city;

    @Size(min = 2, max = 2, message = "State must be a 2-character abbreviation")
    @JsonDeserialize(using = StateDeserializer.class)
    @Schema(description = "New two-letter state abbreviation.", example = "NY", minLength = 2, maxLength = 2)
    private String state;

    @Size(min = 8, max = 8, message = "Unavailable ZipCode")
    @JsonDeserialize(using = ZipCodeDeserializer.class)
    @Schema(description = "New eight-digit ZIP code without separators.", example = "10000000", minLength = 8, maxLength = 8)
    private String zipCode;


}
