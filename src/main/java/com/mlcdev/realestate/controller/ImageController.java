package com.mlcdev.realestate.controller;

import com.mlcdev.realestate.dto.ImageDTO;
import com.mlcdev.realestate.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("v1/properties/{propertyId}/images")
public class ImageController {

    private final ImageService imageService;

    @GetMapping
    public ResponseEntity<List<ImageDTO>> findAllImages(@PathVariable UUID propertyId){
        List<ImageDTO> images = imageService.findAllImages(propertyId);
        return ResponseEntity.ok(images);
    }

    @GetMapping(value = "/primary")
    public ResponseEntity<ImageDTO> findPrimaryImage(@PathVariable UUID propertyId){
        ImageDTO dto = imageService.findPrimaryImage(propertyId);
        return ResponseEntity.ok(dto);
    }



}
