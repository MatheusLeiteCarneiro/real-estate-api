package com.mlcdev.realestate.service;

import com.mlcdev.realestate.dto.UserCreateDTO;
import com.mlcdev.realestate.dto.UserDTO;
import com.mlcdev.realestate.dto.UserPatchDTO;
import com.mlcdev.realestate.entities.Role;
import com.mlcdev.realestate.entities.User;
import com.mlcdev.realestate.exception.BusinessRuleException;
import com.mlcdev.realestate.exception.ConflictException;
import com.mlcdev.realestate.exception.NotFoundException;
import com.mlcdev.realestate.mapper.UserMapper;
import com.mlcdev.realestate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDTO findById(UUID userId){
        log.debug("Retrieving user with ID: {}", userId);
        User user = findUserById(userId);
        return UserMapper.entityToDTO(user);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable){
        log.debug("Retrieving users page {}, size {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.map(UserMapper::entityToDTO);
    }

    @Transactional
    public UserDTO create(UserCreateDTO createDTO){
        checkUsernameAvailable(createDTO.getUsername());
        log.info("Creating user with username: {}", createDTO.getUsername());
        User user = User.builder()
                 .username(createDTO.getUsername())
                 .password(passwordEncoder.encode(createDTO.getPassword()))
                 .build();

         user.addRole(Role.ROLE_BROKER);

         User createdUser = userRepository.save(user);
        log.info("User successfully created with ID: {}", createdUser.getId());
         return UserMapper.entityToDTO(createdUser);
    }

    @Transactional
    public UserDTO update(UserPatchDTO patchDTO, UUID userId){
        log.info("Patching user with id: {}", userId);
        User user = findUserById(userId);
        if(patchDTO.getUsername() != null && !patchDTO.getUsername().isBlank()){
            checkUsernameAvailable(patchDTO.getUsername());
            user.changeUsername(patchDTO.getUsername());
        }
        if(patchDTO.getPassword() != null && !patchDTO.getPassword().isBlank()){
            user.changePassword(passwordEncoder.encode(patchDTO.getPassword()));
        }
        User patchedUser = userRepository.save(user);
        log.info("User with ID: {} successfully patched", userId);
        return UserMapper.entityToDTO(patchedUser);
    }

    @Transactional
    public UserDTO toggleActive(UUID userId){
        log.info("Toggling active status for user with ID: {}", userId);
        User user = findUserById(userId);
        if(user.getAuthorities().contains(Role.ROLE_ADMIN)){
            log.warn("The ADMIN user cannot be deactivated");
            throw new BusinessRuleException("Admin user cannot be deactivated");
        }
        user.toggleActive();
        User savedUser = userRepository.save(user);
        log.info("User with ID : {} , active status changed to {}", userId, savedUser.isActive());
        return UserMapper.entityToDTO(savedUser);

    }

    private User findUserById(UUID userId){
        log.debug("Retrieving user with ID: {} from repository", userId);
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with Id: " + userId + " not found"));
    }

    private void checkUsernameAvailable(String username){
        if(userRepository.existsByUsername(username)){
            log.warn("Attempt to use unavailable username: {}", username);
            throw new ConflictException("Username not available");
        }
    }

}
