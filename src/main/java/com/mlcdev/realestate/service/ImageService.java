package com.mlcdev.realestate.service;

import com.mlcdev.realestate.dto.ImageDTO;
import com.mlcdev.realestate.entities.Image;
import com.mlcdev.realestate.exception.NotFoundException;
import com.mlcdev.realestate.mapper.ImageMapper;
import com.mlcdev.realestate.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final FileStorageService fileStorageService;
    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    public List<ImageDTO> findAllImages(UUID propertyId){
        List<Image> imageList = imageRepository.findAllByPropertyId(propertyId);
        return imageList.stream().sorted(Comparator.comparing(Image::isPrimary).reversed()).map(ImageMapper::entityToDTO).toList();
    }

}
