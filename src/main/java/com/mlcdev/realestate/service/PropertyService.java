package com.mlcdev.realestate.service;

import com.mlcdev.realestate.dto.PropertyCreateDTO;
import com.mlcdev.realestate.dto.PropertyDetailDTO;
import com.mlcdev.realestate.dto.PropertyPatchDTO;
import com.mlcdev.realestate.dto.PropertySummaryDTO;
import com.mlcdev.realestate.entities.Property;
import com.mlcdev.realestate.exception.NotFoundException;
import com.mlcdev.realestate.mapper.PropertyMapper;
import com.mlcdev.realestate.repository.PropertyRepository;
import com.mlcdev.realestate.repository.UserRepository;
import com.mlcdev.realestate.security.OwnershipValidator;
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
        OwnershipValidator.propertyVerifyBrokerPermission(property, brokerId, isAdmin);
        Property updatedProperty = propertyRepository.saveAndFlush(PropertyMapper.applyPatchDTOToEntity(dto, property));
        log.info("Property with ID: {} successfully patched", propertyId);
        return PropertyMapper.entityToDetailDTO(updatedProperty);
    }

    @Transactional(readOnly = true)
    public Page<PropertySummaryDTO> findBrokerProperties(Pageable pageable, UUID brokerId) {
        log.debug("Retrieving properties from the broker with id: {}", brokerId);
        if(!userRepository.existsById(brokerId)){
            log.warn("Broker with ID {} doesn't exist", brokerId);
            throw new NotFoundException("Broker with Id: " + brokerId + " doesn't exist");
        }
        Page<Property> brokerProperties = propertyRepository.findPropertiesByBroker(userRepository.getReferenceById(brokerId), pageable);
        log.debug("{} properties retrieved from broker with ID: {}", brokerProperties.getSize(), brokerId);
        return brokerProperties.map(PropertyMapper::entityToSummaryDTO);
    }

    @Transactional
    public void delete(UUID propertyId, UUID brokerId, boolean isAdmin) {
        log.info("Deleting property with id: {}", propertyId);
        Property property = propertyByIdOrElseThrow(propertyId);
        OwnershipValidator.propertyVerifyBrokerPermission(property, brokerId, isAdmin);
        imageService.deleteAllImagesForProperty(propertyId);
        propertyRepository.delete(property);
        log.info("Property with ID: {} successfully deleted", propertyId);
    }

    private Property propertyByIdOrElseThrow(UUID id){
        return propertyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Property with ID: " + id + " not found"));
    }




}


