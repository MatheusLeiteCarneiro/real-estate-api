package com.mlcdev.realestate.dto;

import com.mlcdev.realestate.entities.PropertyType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyCreateDTO {

    @NotBlank(message = "This field should not be left blank")
    @Size(min = 5, message = "The Title should contain at least 5 characters")
    private String title;

    @NotBlank(message = "This field should not be left blank")
    @Size(min = 10, message = "The description should contain at least 10 characters")
    private String description;

    @NotNull(message = "This field should not be left null")
    @Positive(message = "This field should be greater than 0")
    private BigDecimal price;

    @NotNull(message = "This field should not be left null")
    private PropertyType type;

    @PositiveOrZero(message = "This field cannot be negative")
    private Integer suites;

    @PositiveOrZero(message = "This field cannot be negative")
    private Integer bedrooms;

    @PositiveOrZero(message = "This field cannot be negative")
    private Integer bathrooms;

    @NotNull(message = "This field should not be left null")
    @Positive(message = "This field should be greater than 0")
    private Double area;

    @PositiveOrZero(message = "This field cannot be negative")
    private Integer parkingSpots;

    @Valid
    @NotNull(message = "This field should not be left null")
    private AddressDTO address;
}
