package com.mlcdev.realestate.service;

import com.mlcdev.realestate.dto.UserCreateDTO;
import com.mlcdev.realestate.dto.UserDTO;
import com.mlcdev.realestate.entities.Role;
import com.mlcdev.realestate.entities.User;
import com.mlcdev.realestate.exception.ConflictException;
import com.mlcdev.realestate.exception.NotFoundException;
import com.mlcdev.realestate.mapper.UserMapper;
import com.mlcdev.realestate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDTO findById(UUID userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with Id: " + userId + " not found"));
        return UserMapper.entityToDTO(user);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable){
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(UserMapper::entityToDTO);
    }

    @Transactional
    public UserDTO create(UserCreateDTO createDTO){
         if(userRepository.existsByUsername(createDTO.getUsername())){
             throw new ConflictException("Username not available");
         }
         User user = User.builder()
                 .username(createDTO.getUsername())
                 .password(passwordEncoder.encode(createDTO.getPassword()))
                 .build();

         user.addRole(Role.ROLE_BROKER);

         return UserMapper.entityToDTO(userRepository.save(user));
    }

}
