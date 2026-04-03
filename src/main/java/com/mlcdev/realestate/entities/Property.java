package com.mlcdev.realestate.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "tb_property")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
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

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Image> images = new HashSet<>();


    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;


}
