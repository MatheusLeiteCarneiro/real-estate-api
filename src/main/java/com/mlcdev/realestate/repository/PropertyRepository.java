package com.mlcdev.realestate.repository;

import com.mlcdev.realestate.entities.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PropertyRepository extends JpaRepository<Property, UUID> {

    @Query("SELECT p FROM Property p WHERE p.broker.id = :brokerId")
    Page<Property> findPropertiesByBrokerId(UUID brokerId, Pageable pageable);

}
