package com.mlcdev.realestate.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tb_property")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private PropertyType type;
    @Enumerated(EnumType.STRING)

    private PropertyStatus status;
    private Integer suites;
    private Integer bedrooms;
    private Integer bathrooms;
    private Double area;
    private Integer parkingSpots;

    @Embedded
    private Address address;

}
