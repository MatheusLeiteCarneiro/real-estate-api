package com.mlcdev.realestate.controller;

import com.mlcdev.realestate.dto.ImageDTO;
import com.mlcdev.realestate.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ImageDTO>> postImages(@PathVariable UUID propertyId, @RequestPart("files") List<MultipartFile> files){
        List<ImageDTO> imageDTOList = imageService.saveImages(propertyId, files);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(propertyId).toUri();
        return ResponseEntity.created(uri).body(imageDTOList);
    }

    @DeleteMapping(value = "/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable UUID propertyId, @PathVariable UUID imageId){
        imageService.deleteImage(propertyId, imageId);
        return ResponseEntity.noContent().build();
    }





}
