package com.mlcdev.realestate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mlcdev.realestate.util.StateDeserializer;
import com.mlcdev.realestate.util.ZipCodeDeserializer;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressPatchDTO {

    private String street;

    private String number;

    private String complement;

    private String neighborhood;

    private String city;

    @Size(min = 2, max = 2, message = "State must be a 2-character abbreviation")
    @JsonDeserialize(using = StateDeserializer.class)
    private String state;

    @Size(min = 8, max = 8, message = "Unavailable ZipCode")
    @JsonDeserialize(using = ZipCodeDeserializer.class)
    private String zipCode;


}
