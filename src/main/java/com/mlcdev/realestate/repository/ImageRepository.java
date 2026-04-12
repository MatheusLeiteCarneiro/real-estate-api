package com.mlcdev.realestate.repository;

import com.mlcdev.realestate.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {

    List<Image> findAllByPropertyId(UUID propertyId);

    Optional<Image> findByPropertyIdAndIsPrimaryTrue(UUID propertyId);

    List<Image> findAllByPropertyIdAndIsPrimaryFalse(UUID propertyId);

}
