package com.mlcdev.realestate.service;

import com.mlcdev.realestate.dto.PropertyDTO;
import com.mlcdev.realestate.entities.Property;
import com.mlcdev.realestate.exception.NotFoundException;
import com.mlcdev.realestate.mapper.PropertyMapper;
import com.mlcdev.realestate.repository.PropertyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class PropertyService {

    private PropertyRepository propertyRepository;

    public Page<PropertyDTO> findAll(Pageable pageable){
        Page<Property> properties = propertyRepository.findAll(pageable);
        return properties.map(PropertyMapper::entityToDTO);
    }

    public PropertyDTO findById(UUID id){
        return PropertyMapper.entityToDTO(propertyRepository.findById(id).orElseThrow(() -> new NotFoundException("Employee with ID: " + id + " not found")));
    }


}


