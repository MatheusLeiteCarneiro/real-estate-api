package com.mlcdev.realestate.dto;

import com.mlcdev.realestate.entities.PropertyStatus;
import com.mlcdev.realestate.entities.PropertyType;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PropertySummaryDTO {

    private UUID id;
    private String title;
    private BigDecimal price;

    private PropertyType type;
    private PropertyStatus status;

    private Integer suites;
    private Integer bedrooms;
    private Integer bathrooms;
    private Double area;
    private Integer parkingSpots;

    private String city;
    private String state;

    private ImageDTO primaryImage;


}
