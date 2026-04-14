package com.mlcdev.realestate.controller;

import com.mlcdev.realestate.dto.LoginRequest;
import com.mlcdev.realestate.dto.LoginResponse;
import com.mlcdev.realestate.entities.User;
import com.mlcdev.realestate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
public class TokenController {

    @Value("${security.jwt.duration}")
    private Long jwtDurationSeconds;
    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;




    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
       Optional<User> userOptional = userRepository.findByUsername(loginRequest.username());

       if(userOptional.isEmpty() || !userOptional.get().isLoginCorrect(loginRequest, passwordEncoder)){
           throw new BadCredentialsException("User or password is invalid");
       }

       Instant now = Instant.now();

       JwtClaimsSet claims = JwtClaimsSet.builder()
               .issuer("real-estate-api")
               .subject(userOptional.get().getId().toString())
               .issuedAt(now)
               .expiresAt(now.plusSeconds(jwtDurationSeconds))
               .build();

       String jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

       return ResponseEntity.ok(new LoginResponse(jwtValue, jwtDurationSeconds));
    }
 }
