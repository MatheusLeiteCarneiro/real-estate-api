package com.mlcdev.realestate.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

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
    @Builder.Default
    private PropertyStatus status = PropertyStatus.AVAILABLE;

    private Integer suites;
    private Integer bedrooms;
    private Integer bathrooms;
    private Double area;
    private Integer parkingSpots;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Image> images = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "broker_id", nullable = false)
    private User broker;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;


    public void addImage(Image image){
        image.setProperty(this);
        images.add(image);
    }

        public void removeImage(Image image){
        image.setProperty(null);
        images.remove(image);
    }
}
