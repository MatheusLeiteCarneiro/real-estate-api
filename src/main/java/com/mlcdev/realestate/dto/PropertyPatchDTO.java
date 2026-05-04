package com.mlcdev.realestate.dto;

import com.mlcdev.realestate.entities.PropertyCategory;
import com.mlcdev.realestate.entities.TransactionType;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

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

    private TransactionType transactionType;

    private PropertyCategory category;

    @PositiveOrZero(message = "This field cannot be negative")
    private Integer suites;

    @PositiveOrZero(message = "This field cannot be negative")
    private Integer bedrooms;

    @PositiveOrZero(message = "This field cannot be negative")
    private Integer bathrooms;

    @Positive(message = "This field should be greater than 0")
    private BigDecimal area;

    @PositiveOrZero(message = "This field cannot be negative")
    private Integer parkingSpots;

    private AddressPatchDTO address;
}
