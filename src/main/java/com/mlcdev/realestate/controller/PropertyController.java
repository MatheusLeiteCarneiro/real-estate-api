package com.mlcdev.realestate.controller;


import com.mlcdev.realestate.dto.PropertyDTO;
import com.mlcdev.realestate.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/properties")
public class PropertyController {

    private final PropertyService propertyService;

    @GetMapping
    public ResponseEntity<Page<PropertyDTO>> findAllProperties(@ParameterObject @PageableDefault(page = 0, size = 10) Pageable pageable){
        Page<PropertyDTO> propertyPage = propertyService.findAll(pageable);
        return ResponseEntity.ok(propertyPage);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PropertyDTO> findPropertyById(@PathVariable UUID id){
        PropertyDTO propertyDTO = propertyService.findById(id);
        return ResponseEntity.ok(propertyDTO);
    }

}
