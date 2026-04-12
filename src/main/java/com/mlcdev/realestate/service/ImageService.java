package com.mlcdev.realestate.service;

import com.mlcdev.realestate.dto.ImageDTO;
import com.mlcdev.realestate.entities.Image;
import com.mlcdev.realestate.entities.Property;
import com.mlcdev.realestate.exception.ResourceMismatchException;
import com.mlcdev.realestate.exception.EmptyResourceException;
import com.mlcdev.realestate.exception.FileStorageException;
import com.mlcdev.realestate.exception.NotFoundException;
import com.mlcdev.realestate.mapper.ImageMapper;
import com.mlcdev.realestate.repository.ImageRepository;
import com.mlcdev.realestate.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
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
        Image primaryImage = imageRepository.findByPropertyIdAndIsPrimaryTrue(propertyId).orElseThrow(() -> new NotFoundException("Primary Image not found for property with Id: " + propertyId));
        return ImageMapper.entityToDTO(primaryImage);
    }

    @Transactional
    public List<ImageDTO> saveImages(UUID propertyId, List<MultipartFile> files){

        if(files.isEmpty()){
            throw new EmptyResourceException("The file list is empty");
        }

        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new NotFoundException("Property with ID: " + propertyId + " not found"));
        boolean noPrimary = imageRepository.findByPropertyIdAndIsPrimaryTrue(propertyId).isEmpty();

        List<Image> images = files.stream().map(_ -> Image.builder().isPrimary(false).property(property).build()).collect(Collectors.toCollection(ArrayList::new));

        if (noPrimary){
            images.getFirst().setPrimary(true);
        }
        List<String> uploadedIdentifiers = new ArrayList<>();
        try{

            for (int i = 0; i < files.size(); i++) {
                Map<String, String> fileInformation = fileStorageService.uploadFile(files.get(i).getBytes(), propertyImageFolder);
                uploadedIdentifiers.add(fileInformation.get("fileIdentifier"));
                images.get(i).setUrl(fileInformation.get("url"));
                images.get(i).setFileIdentifier(fileInformation.get("fileIdentifier"));

            }
            return imageRepository.saveAll(images).stream().map(ImageMapper::entityToDTO).toList();

        }catch (Exception e){
            uploadedIdentifiers.forEach(fileIdentifier -> {
                try {
                    fileStorageService.deleteFile(fileIdentifier);
                }
                catch (Exception ignored){ log.warn("Failed to delete file during rollback. Identifier: {}",fileIdentifier);}
            });
            throw new FileStorageException("Error occurred on the saving of the files", e);
        }

    }

    @Transactional
    public void deleteImage(UUID propertyId, UUID imageId){
        Image image = imageRepository.findById(imageId).orElseThrow(() -> new NotFoundException("Image with ID: " + imageId + " not found"));
       if(!image.getProperty().getId().equals(propertyId)){
           throw new ResourceMismatchException("Image with ID: " + imageId + " it's not from the property with id: " + propertyId);
       }

       String fileIdentifier = image.getFileIdentifier();
       imageRepository.delete(image);

       if(image.isPrimary()){
           List<Image> propertyImages = imageRepository.findAllByPropertyIdAndIsPrimaryFalse(propertyId);

           if(!propertyImages.isEmpty()){
            Image newPrimaryImage = propertyImages.getFirst();
            newPrimaryImage.setPrimary(true);
           }

       }

        try {
            fileStorageService.deleteFile(fileIdentifier);
        }
        catch (Exception e){
            log.warn("Failed to delete file during deletion. Identifier: {}",fileIdentifier);}
    }

}
