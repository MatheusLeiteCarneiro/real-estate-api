package com.mlcdev.realestate.service;

import com.mlcdev.realestate.dto.PropertyCreateDTO;
import com.mlcdev.realestate.dto.PropertyDetailDTO;
import com.mlcdev.realestate.dto.PropertyPatchDTO;
import com.mlcdev.realestate.dto.PropertySummaryDTO;
import com.mlcdev.realestate.entities.Property;
import com.mlcdev.realestate.exception.NotFoundException;
import com.mlcdev.realestate.mapper.PropertyMapper;
import com.mlcdev.realestate.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    @Transactional(readOnly = true)
    public Page<PropertySummaryDTO> findAll(Pageable pageable){
        Page<Property> properties = propertyRepository.findAll(pageable);
        return properties.map(PropertyMapper::entityToSummaryDTO);
    }

    @Transactional(readOnly = true)
    public PropertyDetailDTO findById(UUID id){
        return PropertyMapper.entityToDetailDTO(propertyRepository.findById(id).orElseThrow(() -> new NotFoundException("Property with ID: " + id + " not found")));
    }

    @Transactional
    public PropertyDetailDTO create(PropertyCreateDTO createDTO){
        Property property = PropertyMapper.createDTOToEntity(createDTO);
        return PropertyMapper.entityToDetailDTO(propertyRepository.saveAndFlush(property));

    }

    @Transactional
    public PropertyDetailDTO update(UUID id, PropertyPatchDTO dto){
        Property entity = propertyRepository.findById(id).orElseThrow(() -> new NotFoundException("Property with ID: " + id + " not found"));
        Property updatedEntity = PropertyMapper.applyPatchDTOToEntity(dto, entity);
        return PropertyMapper.entityToDetailDTO(propertyRepository.saveAndFlush(updatedEntity));
    }

    @Transactional
    public void delete(UUID id) {
        Property entity = propertyRepository.findById(id).orElseThrow(() -> new NotFoundException("Property with ID: " + id + " not found"));
        propertyRepository.delete(entity);
    }
}


