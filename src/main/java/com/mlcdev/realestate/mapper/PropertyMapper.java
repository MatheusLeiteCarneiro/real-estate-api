package com.mlcdev.realestate.mapper;

import com.mlcdev.realestate.dto.PropertyCreateDTO;
import com.mlcdev.realestate.dto.PropertyDetailDTO;
import com.mlcdev.realestate.dto.PropertyPatchDTO;
import com.mlcdev.realestate.dto.PropertySummaryDTO;
import com.mlcdev.realestate.entities.Image;
import com.mlcdev.realestate.entities.Property;

import java.util.stream.Collectors;

public class PropertyMapper {

    private PropertyMapper(){}

    public static PropertyDetailDTO entityToDetailDTO(Property entity){

        return PropertyDetailDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .type(entity.getType())
                .status(entity.getStatus())
                .suites(entity.getSuites())
                .bedrooms(entity.getBedrooms())
                .bathrooms(entity.getBathrooms())
                .area(entity.getArea())
                .parkingSpots(entity.getParkingSpots())
                .address(AddressMapper.entityToDTO(entity.getAddress()))
                .images(entity.getImages().stream().map(ImageMapper::entityToDTO).collect(Collectors.toList()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();


    }

    public static PropertySummaryDTO entityToSummaryDTO(Property entity){

        return PropertySummaryDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .price(entity.getPrice())
                .type(entity.getType())
                .status(entity.getStatus())
                .suites(entity.getSuites())
                .bedrooms(entity.getBedrooms())
                .bathrooms(entity.getBathrooms())
                .area(entity.getArea())
                .parkingSpots(entity.getParkingSpots())
                .city(entity.getAddress().getCity())
                .state(entity.getAddress().getState())
                .primaryImage(entity.getImages().stream().filter(Image::isPrimary).findFirst().map(ImageMapper::entityToDTO).orElse(null))
                .build();


    }

    public static Property createDTOToEntity(PropertyCreateDTO dto){
        return Property.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .type(dto.getType())
                .suites(dto.getSuites())
                .bedrooms(dto.getBedrooms())
                .bathrooms(dto.getBathrooms())
                .area(dto.getArea())
                .parkingSpots(dto.getParkingSpots())
                .address(AddressMapper.dtoToEntity(dto.getAddress()))
                .build();

    }


    public static Property applyPatchDTOToEntity(PropertyPatchDTO dto, Property entity){
       if(dto.getTitle() != null){entity.setTitle(dto.getTitle());}
       if(dto.getDescription() != null){entity.setDescription(dto.getDescription());}
       if(dto.getPrice() != null){entity.setPrice(dto.getPrice());}
       if(dto.getType() != null){entity.setType(dto.getType());}
       if(dto.getStatus() != null){entity.setStatus(dto.getStatus());}
       if(dto.getSuites() != null){entity.setSuites(dto.getSuites());}
       if(dto.getBedrooms() != null){entity.setBedrooms(dto.getBedrooms());}
       if(dto.getBathrooms() != null){entity.setBathrooms(dto.getBathrooms());}
       if(dto.getArea() != null){entity.setArea(dto.getArea());}
       if(dto.getParkingSpots() != null){entity.setParkingSpots(dto.getParkingSpots());}
       if(dto.getAddress() != null){entity.setAddress(AddressMapper.dtoToEntity(dto.getAddress()));}
       return entity;
    }

}
