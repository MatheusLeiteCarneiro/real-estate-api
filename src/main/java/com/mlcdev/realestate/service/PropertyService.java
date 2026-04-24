package com.mlcdev.realestate.service;

import com.mlcdev.realestate.dto.PropertyCreateDTO;
import com.mlcdev.realestate.dto.PropertyDetailDTO;
import com.mlcdev.realestate.dto.PropertyPatchDTO;
import com.mlcdev.realestate.dto.PropertySummaryDTO;
import com.mlcdev.realestate.entities.Property;
import com.mlcdev.realestate.exception.BusinessRuleException;
import com.mlcdev.realestate.exception.NotFoundException;
import com.mlcdev.realestate.mapper.PropertyMapper;
import com.mlcdev.realestate.repository.PropertyRepository;
import com.mlcdev.realestate.repository.UserRepository;
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
    private final UserRepository userRepository;

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
    public PropertyDetailDTO create(PropertyCreateDTO createDTO, UUID brokerId){
        log.info("Creating property with title: {}", createDTO.getTitle());
        Property property = PropertyMapper.createDTOToEntity(createDTO);
        property.setBroker(userRepository.getReferenceById(brokerId));
        Property savedProperty = propertyRepository.saveAndFlush(property);
        log.info("Property successfully created with ID: {}", savedProperty.getId());
        return PropertyMapper.entityToDetailDTO(savedProperty);
    }

    @Transactional
    public PropertyDetailDTO update(UUID propertyId, PropertyPatchDTO dto, UUID brokerId, boolean isAdmin){
        log.info("Patching property with id: {}", propertyId);
        Property property = propertyByIdOrElseThrow(propertyId);
        validateUserAdminOrPropertyBroker(property, brokerId, isAdmin);
        Property updatedProperty = propertyRepository.saveAndFlush(PropertyMapper.applyPatchDTOToEntity(dto, property));
        log.info("Property with ID: {} successfully patched", propertyId);
        return PropertyMapper.entityToDetailDTO(updatedProperty);
    }

    @Transactional
    public void delete(UUID propertyId, UUID brokerId, boolean isAdmin) {
        log.info("Deleting property with id: {}", propertyId);
        Property property = propertyByIdOrElseThrow(propertyId);
        validateUserAdminOrPropertyBroker(property, brokerId, isAdmin);
        imageService.deleteAllImagesForProperty(propertyId);
        propertyRepository.delete(property);
        log.info("Property with ID: {} successfully deleted", propertyId);
    }

    private Property propertyByIdOrElseThrow(UUID id){
        return propertyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Property with ID: " + id + " not found"));
    }

    private void validateUserAdminOrPropertyBroker(Property property, UUID brokerId, boolean isAdmin){
        if(!isAdmin && !property.getBroker().getId().equals(brokerId)){
            log.warn("User with ID: {} doesn't have the permission to modify the property with ID: {}", brokerId, property.getId());
            throw new BusinessRuleException("User doesn't have the permission to modify the property");
        }
    }
}


