package com.mlcdev.realestate.mapper;

import com.mlcdev.realestate.dto.PropertyDTO;
import com.mlcdev.realestate.entities.Property;

import java.util.stream.Collectors;

public class PropertyMapper {

    private PropertyMapper(){}

    public static PropertyDTO entityToDTO(Property entity){

        return PropertyDTO.builder()
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

}
