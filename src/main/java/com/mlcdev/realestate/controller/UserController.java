package com.mlcdev.realestate.controller;

import com.mlcdev.realestate.dto.UserDetailDTO;
import com.mlcdev.realestate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserDetailDTO> findById(@PathVariable UUID userId){
        UserDetailDTO dto = userService.findById(userId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<UserDetailDTO>> findAll(@ParameterObject @PageableDefault(page = 0, size = 10) Pageable pageable){
        Page<UserDetailDTO> dtoPage = userService.findAll(pageable);
        return ResponseEntity.ok(dtoPage);
    }
}
