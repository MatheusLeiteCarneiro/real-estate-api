package com.mlcdev.realestate.repository;

import com.mlcdev.realestate.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {

    List<Image> findAllByPropertyId(UUID propertyId);

    @Query("SELECT img FROM Image img WHERE img.property.id = :propertyId AND img.isPrimary = true")
    Optional<Image> findPropertyPrimaryImage(UUID propertyId);
}
