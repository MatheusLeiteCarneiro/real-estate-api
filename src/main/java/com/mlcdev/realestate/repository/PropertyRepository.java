package com.mlcdev.realestate.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mlcdev.realestate.entities.Property;

public interface PropertyRepository extends JpaRepository<Property, UUID> {
    
}
