package com.mlcdev.realestate.dto;

import com.mlcdev.realestate.entities.PropertyStatus;
import com.mlcdev.realestate.entities.PropertyType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyPatchDTO {

    @Size(min = 5, message = "The Title should contain at least 5 characters")
    private String title;

    @Size(min = 10, message = "The description should contain at least 10 characters")
    private String description;

    @Positive(message = "This field should be greater than 0")
    private BigDecimal price;

    private PropertyType type;

    private PropertyStatus status;

    @PositiveOrZero(message = "This field cannot be negative")
    private Integer suites;

    @PositiveOrZero(message = "This field cannot be negative")
    private Integer bedrooms;

    @PositiveOrZero(message = "This field cannot be negative")
    private Integer bathrooms;

    @Positive(message = "This field should be greater than 0")
    private Double area;

    @PositiveOrZero(message = "This field cannot be negative")
    private Integer parkingSpots;

    private AddressDTO address;
}
