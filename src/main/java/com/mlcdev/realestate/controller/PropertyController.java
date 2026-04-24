package com.mlcdev.realestate.controller;


import com.mlcdev.realestate.dto.PropertyCreateDTO;
import com.mlcdev.realestate.dto.PropertyDetailDTO;
import com.mlcdev.realestate.dto.PropertyPatchDTO;
import com.mlcdev.realestate.dto.PropertySummaryDTO;
import com.mlcdev.realestate.entities.Role;
import com.mlcdev.realestate.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/properties")
public class PropertyController {

    private final PropertyService propertyService;

    @GetMapping
    public ResponseEntity<Page<PropertySummaryDTO>> findAllProperties(@ParameterObject @PageableDefault(page = 0, size = 10) Pageable pageable){
        Page<PropertySummaryDTO> propertyPage = propertyService.findAll(pageable);
        return ResponseEntity.ok(propertyPage);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PropertyDetailDTO> findPropertyById(@PathVariable UUID id){
        PropertyDetailDTO propertyDTO = propertyService.findById(id);
        return ResponseEntity.ok(propertyDTO);
    }

    @PreAuthorize("hasRole('BROKER')")
    @PostMapping
    public ResponseEntity<PropertyDetailDTO> createProperty(@Valid @RequestBody PropertyCreateDTO dto, @AuthenticationPrincipal Jwt jwt){
        PropertyDetailDTO createdDTO = propertyService.create(dto, getUserIdByJwt(jwt));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(createdDTO);
    }

    @PreAuthorize("hasRole('BROKER')")
    @PatchMapping(value = "/{id}")
    public ResponseEntity<PropertyDetailDTO> patchProperty(@Valid @RequestBody PropertyPatchDTO dto, @PathVariable UUID id, @AuthenticationPrincipal Jwt jwt){
        PropertyDetailDTO responseDto = propertyService.update(id ,dto, getUserIdByJwt(jwt), isAdmin(jwt));
        return ResponseEntity.ok(responseDto);
    }
    @PreAuthorize("hasRole('BROKER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt){
        propertyService.delete(id, getUserIdByJwt(jwt), isAdmin(jwt));
        return ResponseEntity.noContent().build();
    }


    private boolean isAdmin(Jwt jwt){
        List<String> authorities =  jwt.getClaimAsStringList("authorities");
        return authorities != null && authorities.contains(Role.ROLE_ADMIN.name());
    }

    private UUID getUserIdByJwt(Jwt jwt){
        return UUID.fromString(jwt.getSubject());
    }
}
