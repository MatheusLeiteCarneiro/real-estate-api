package com.mlcdev.realestate.repository;

import java.util.UUID;

import com.mlcdev.realestate.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mlcdev.realestate.entities.Property;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, UUID> {

    Page<Property> findPropertiesByBroker(User broker, Pageable pageable);
    
}
