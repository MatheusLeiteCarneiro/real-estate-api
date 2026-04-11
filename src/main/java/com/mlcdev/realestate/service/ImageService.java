package com.mlcdev.realestate.service;

import com.mlcdev.realestate.dto.ImageDTO;
import com.mlcdev.realestate.entities.Image;
import com.mlcdev.realestate.entities.Property;
import com.mlcdev.realestate.exception.NotFoundException;
import com.mlcdev.realestate.mapper.ImageMapper;
import com.mlcdev.realestate.repository.ImageRepository;
import com.mlcdev.realestate.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ImageService {

    @Value("${property.image.folder}")
    private String propertyImageFolder;
    private final FileStorageService fileStorageService;
    private final ImageRepository imageRepository;
    private final PropertyRepository propertyRepository;


    @Transactional(readOnly = true)
    public List<ImageDTO> findAllImages(UUID propertyId){
        List<Image> imageList = imageRepository.findAllByPropertyId(propertyId);
        return imageList.stream().sorted(Comparator.comparing(Image::isPrimary).reversed()).map(ImageMapper::entityToDTO).toList();
    }

    @Transactional(readOnly = true)
    public ImageDTO findPrimaryImage(UUID propertyId){
        Image primaryImage = imageRepository.findByPropertyIdAndIsPrimaryTrue(propertyId).orElseThrow(() -> new NotFoundException("Image not found"));
        return ImageMapper.entityToDTO(primaryImage);
    }

    @Transactional
    public List<ImageDTO> saveImages(UUID propertyId, List<MultipartFile> files){
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new NotFoundException("Property with ID: " + propertyId + " not found"));
        boolean noPrimary = imageRepository.findByPropertyIdAndIsPrimaryTrue(propertyId).isEmpty();

        List<Image> images = files.stream().map(file -> Image.builder().isPrimary(false).property(property).build()).collect(Collectors.toCollection(ArrayList::new));

        if (noPrimary){
            images.getFirst().setPrimary(true);
        }

        for(int i = 0; i < files.size(); i++){
            String url = fileStorageService.uploadFile(files.get(i), propertyImageFolder);
            images.get(i).setUrl(url);
        }

        return imageRepository.saveAll(images).stream().map(ImageMapper::entityToDTO).toList();

    }

}
