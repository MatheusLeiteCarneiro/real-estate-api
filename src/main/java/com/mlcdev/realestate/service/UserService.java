package com.mlcdev.realestate.service;

import com.mlcdev.realestate.dto.UserCreateDTO;
import com.mlcdev.realestate.dto.UserDTO;
import com.mlcdev.realestate.dto.UserPatchDTO;
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
        User user = findUserById(userId);
        return UserMapper.entityToDTO(user);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable){
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(UserMapper::entityToDTO);
    }

    @Transactional
    public UserDTO create(UserCreateDTO createDTO){
        checkUsernameAvailable(createDTO.getUsername());
         User user = User.builder()
                 .username(createDTO.getUsername())
                 .password(passwordEncoder.encode(createDTO.getPassword()))
                 .build();

         user.addRole(Role.ROLE_BROKER);

         return UserMapper.entityToDTO(userRepository.save(user));
    }

    @Transactional
    public UserDTO update(UserPatchDTO patchDTO, UUID userId){
        User user = findUserById(userId);
        if(patchDTO.getUsername() != null && !patchDTO.getUsername().isBlank()){
            checkUsernameAvailable(patchDTO.getUsername());
            user.changeUsername(patchDTO.getUsername());
        }
        if(patchDTO.getPassword() != null && !patchDTO.getPassword().isBlank()){
            user.changePassword(passwordEncoder.encode(patchDTO.getPassword()));
        }
        return UserMapper.entityToDTO(userRepository.save(user));
    }

    @Transactional
    public UserDTO toggleActive(UUID userId){
        User user = findUserById(userId);
        user.toggleActive();
        return UserMapper.entityToDTO(user);

    }

    private User findUserById(UUID userId){
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with Id: " + userId + " not found"));
    }

    private void checkUsernameAvailable(String username){
        if(userRepository.existsByUsername(username)){
            throw new ConflictException("Username not available");
        }
    }

}
