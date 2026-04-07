package com.mlcdev.realestate.mapper;

import com.mlcdev.realestate.dto.AddressDTO;
import com.mlcdev.realestate.entities.Address;

public class AddressMapper {

    private AddressMapper() {
    }


    public static AddressDTO entityToDTO(Address address){
        return AddressDTO.builder()
                .street(address.getStreet())
                .number(address.getNumber())
                .complement(address.getComplement())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .build();
    }

    public static Address dtoToEntity(AddressDTO dto){
        return Address.builder()
                .street(dto.getStreet())
                .number(dto.getNumber())
                .complement(dto.getComplement())
                .neighborhood(dto.getNeighborhood())
                .city(dto.getCity())
                .state(dto.getState())
                .zipCode(dto.getZipCode())
                .build();
    }
}
