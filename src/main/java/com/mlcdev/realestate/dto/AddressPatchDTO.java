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

    @Schema(description = "New street name.", example = "Updated Main Street")
    private String street;

    @Schema(description = "New street number.", example = "200")
    private String number;

    @Schema(description = "New additional address information.", example = "Suite 5")
    private String complement;

    @Schema(description = "New neighborhood name.", example = "Central District")
    private String neighborhood;

    @Schema(description = "New city name.", example = "Updated City")
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
