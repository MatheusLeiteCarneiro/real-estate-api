package com.mlcdev.realestate.config;

import com.mlcdev.realestate.entities.Role;
import com.mlcdev.realestate.entities.User;
import com.mlcdev.realestate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class AdminUserConfig implements CommandLineRunner {

    @Value("${admin.username}")
    private String adminUsername;
    @Value("${admin.password}")
    private String adminPassword;

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public void run(String @NonNull ... args) {
        if(userRepository.existsByUsername(adminUsername)){
            log.info("admin user already exist, skipping seed");
        }
        else {
            User adminUser = User.builder()
                    .username(adminUsername)
                    .password(passwordEncoder.encode(adminPassword))
                    .build();
            adminUser.addRole(Role.ROLE_ADMIN);
            userRepository.save(adminUser);
            log.info("Admin user successfully created");
        }


    }
}
