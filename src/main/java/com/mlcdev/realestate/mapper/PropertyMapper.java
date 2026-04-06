package com.mlcdev.realestate.mapper;

import com.mlcdev.realestate.dto.PropertyDetailDTO;
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
                .images(entity.getImages().stream().map(ImageMapper::entityToDTO).collect(Collectors.toSet()))
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

}
