package com.mlcdev.realestate.mapper;

import com.mlcdev.realestate.dto.UserDetailDTO;
import com.mlcdev.realestate.entities.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.stream.Collectors;

public class UserMapper {

    private UserMapper(){}

    public static UserDetailDTO entityToDetailDTO(User entity){
        return UserDetailDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .authorities(entity.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .build();
    }
}
