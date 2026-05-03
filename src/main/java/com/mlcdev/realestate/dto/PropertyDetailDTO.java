package com.mlcdev.realestate.dto;

import com.mlcdev.realestate.entities.PropertyStatus;
import com.mlcdev.realestate.entities.PropertyType;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PropertyDetailDTO {

    private UUID id;
    private String title;
    private String description;
    private BigDecimal price;

    private PropertyType type;
    private PropertyStatus status;

    private Integer suites;
    private Integer bedrooms;
    private Integer bathrooms;
    private BigDecimal area;
    private Integer parkingSpots;

    private AddressDTO address;

    @Builder.Default
    private List<ImageDTO> images = new ArrayList<>();

    private Instant createdAt;
    private Instant updatedAt;
}
