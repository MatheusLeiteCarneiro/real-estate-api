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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final ImageService imageService;

    @Transactional(readOnly = true)
    public Page<PropertySummaryDTO> findAll(Pageable pageable){
        log.debug("Retrieving properties page {}, size {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<Property> properties = propertyRepository.findAll(pageable);
        return properties.map(PropertyMapper::entityToSummaryDTO);
    }

    @Transactional(readOnly = true)
    public PropertyDetailDTO findById(UUID id){
        log.debug("Retrieving property with ID: {}", id);
        return PropertyMapper.entityToDetailDTO(propertyByIdOrElseThrow(id));
    }

    @Transactional
    public PropertyDetailDTO create(PropertyCreateDTO createDTO){
        log.info("Creating property with title: {}", createDTO.getTitle());
        Property savedProperty = propertyRepository.saveAndFlush(PropertyMapper.createDTOToEntity(createDTO));
        log.info("Property successfully created with ID: {}", savedProperty.getId());
        return PropertyMapper.entityToDetailDTO(savedProperty);
    }

    @Transactional
    public PropertyDetailDTO update(UUID id, PropertyPatchDTO dto){
        log.info("Patching property with id: {}", id);
        Property property = propertyByIdOrElseThrow(id);
        Property updatedProperty = propertyRepository.saveAndFlush(PropertyMapper.applyPatchDTOToEntity(dto, property));
        log.info("Property with ID: {} successfully patched", id);
        return PropertyMapper.entityToDetailDTO(updatedProperty);
    }

    @Transactional
    public void delete(UUID id) {
        log.info("Deleting property with id: {}", id);
        Property property = propertyByIdOrElseThrow(id);
        imageService.deleteAllImagesForProperty(id);
        propertyRepository.delete(property);
        log.info("Property with ID: {} successfully deleted", id);
    }

    private Property propertyByIdOrElseThrow(UUID id){
        return propertyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Property with ID: " + id + " not found"));
    }
}


