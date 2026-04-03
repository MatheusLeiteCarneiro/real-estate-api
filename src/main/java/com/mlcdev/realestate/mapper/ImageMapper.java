package com.mlcdev.realestate.mapper;

import com.mlcdev.realestate.dto.ImageDTO;
import com.mlcdev.realestate.entities.Image;

public class ImageMapper {

    private ImageMapper(){}

    public static ImageDTO entityToDTO(Image entity){
        return ImageDTO.builder()
                .id(entity.getId())
                .url(entity.getUrl())
                .property(PropertyMapper.entityToDTO(entity.getProperty()))
                .build();
    }

}
