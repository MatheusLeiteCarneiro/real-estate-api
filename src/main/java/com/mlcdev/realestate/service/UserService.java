package com.mlcdev.realestate.service;

import com.mlcdev.realestate.dto.UserDetailDTO;
import com.mlcdev.realestate.entities.User;
import com.mlcdev.realestate.exception.NotFoundException;
import com.mlcdev.realestate.mapper.UserMapper;
import com.mlcdev.realestate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDetailDTO findById(UUID userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with Id: " + userId + " not found"));
        return UserMapper.entityToDetailDTO(user);
    }
}
