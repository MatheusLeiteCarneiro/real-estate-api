package com.mlcdev.realestate.dto;

import com.mlcdev.realestate.entities.PropertyCategory;
import com.mlcdev.realestate.entities.TransactionType;
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

    @NotNull(message = "Transaction type is required")
    private TransactionType transactionType;

    @NotNull(message = "Category is required")
    private PropertyCategory category;

    @PositiveOrZero(message = "This field cannot be negative")
    private Integer suites;

    @PositiveOrZero(message = "This field cannot be negative")
    private Integer bedrooms;

    @PositiveOrZero(message = "This field cannot be negative")
    private Integer bathrooms;

    @NotNull(message = "This field should not be left null")
    @Positive(message = "This field should be greater than 0")
    private BigDecimal area;

    @PositiveOrZero(message = "This field cannot be negative")
    private Integer parkingSpots;

    @Valid
    @NotNull(message = "This field should not be left null")
    private AddressDTO address;
}
