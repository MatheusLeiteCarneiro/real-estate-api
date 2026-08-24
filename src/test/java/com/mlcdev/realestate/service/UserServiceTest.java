package com.mlcdev.realestate.service;

import com.mlcdev.realestate.dto.UserCreateDTO;
import com.mlcdev.realestate.dto.UserDTO;
import com.mlcdev.realestate.entities.Role;
import com.mlcdev.realestate.entities.User;
import com.mlcdev.realestate.exception.ConflictException;
import com.mlcdev.realestate.repository.UserRepository;
import com.mlcdev.realestate.security.OAuth2AuthorizationCleanupService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {


    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OAuth2AuthorizationCleanupService oAuth2AuthorizationCleanupService;

    @InjectMocks
    private UserService userService;

    @Nested
    @DisplayName("Create User")
    class CreateTests{

        @Test
        @DisplayName("Should create broker with encoded password and public fields")
        void createShouldSaveBrokerAndReturnPublicUserFields() {
            String username = "NewUsername";
            String rawPassword = "StrongPassword@123";
            String encodedPassword = "encoded_password";
            UUID generatedId = UUID.randomUUID();
            Instant createdAt = Instant.now();

            UserCreateDTO createDTO = UserCreateDTO.builder()
                    .username(username)
                    .password(rawPassword)
                    .build();

            when(userRepository.existsByUsername(username)).thenReturn(false);
            when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

            when(userRepository.saveAndFlush(any(User.class)))
                    .thenAnswer(invocation -> {
                        User userToSave = invocation.getArgument(0);
                        userToSave.setId(generatedId);
                        userToSave.setCreatedAt(createdAt);
                        return userToSave;
                    });

            UserDTO result = userService.create(createDTO);

            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository).saveAndFlush(userCaptor.capture());
            verify(passwordEncoder).encode(rawPassword);

            User savedUser = userCaptor.getValue();

            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(generatedId);
            assertThat(result.getUsername()).isEqualTo(username);

            assertThat(savedUser.getPassword())
                    .isEqualTo(encodedPassword)
                    .isNotEqualTo(rawPassword);

            assertThat(savedUser.getAuthorities())
                    .containsExactly(Role.ROLE_BROKER);

            assertThat(result.getAuthorities())
                    .containsExactly(Role.ROLE_BROKER.getAuthority());

            assertThat(result.getCreatedAt()).isEqualTo(createdAt);

            assertThat(result.isActive()).isTrue();
        }

        @Test
        @DisplayName("Should reject duplicate username")
        void createShouldThrowConflictExceptionWhenUsernameAlreadyExists(){
            String username = "NewUsername";
            String rawPassword = "StrongPassword@123";

            UserCreateDTO createDTO = UserCreateDTO.builder()
                    .username(username)
                    .password(rawPassword)
                    .build();

            when(userRepository.existsByUsername(username)).thenReturn(true);

            assertThatThrownBy(() -> userService.create(createDTO))
                    .isInstanceOf(ConflictException.class)
                    .hasMessage("Username not available");

        }
    }

}
