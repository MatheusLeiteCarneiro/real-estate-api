package com.mlcdev.realestate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mlcdev.realestate.util.StateDeserializer;
import com.mlcdev.realestate.util.ZipCodeDeserializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {

    @NotBlank(message = "This field should not be left blank")
    private String street;

    @NotBlank(message = "This field should not be left blank")
    private String number;

    private String complement;

    @NotBlank(message = "This field should not be left blank")
    private String neighborhood;

    @NotBlank(message = "This field should not be left blank")
    private String city;

    @NotNull(message = "This field should not be left null")
    @Size(min = 2, max = 2, message = "State must be a 2-character abbreviation")
    @JsonDeserialize(using = StateDeserializer.class)
    private String state;

    @NotNull(message = "This field should not be left null")
    @Size(min = 8, max = 8, message = "Unavailable ZipCode")
    @JsonDeserialize(using = ZipCodeDeserializer.class)
    private String zipCode;

    
}
