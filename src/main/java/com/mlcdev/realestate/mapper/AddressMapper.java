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
}
