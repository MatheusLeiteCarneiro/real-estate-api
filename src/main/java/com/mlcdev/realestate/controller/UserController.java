package com.mlcdev.realestate.controller;

import com.mlcdev.realestate.dto.UserCreateDTO;
import com.mlcdev.realestate.dto.UserDTO;
import com.mlcdev.realestate.dto.UserPatchDTO;
import com.mlcdev.realestate.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/users")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserDTO> findById(@PathVariable UUID userId){
        UserDTO dto = userService.findById(userId);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(@ParameterObject @PageableDefault(page = 0, size = 10) Pageable pageable){
        Page<UserDTO> dtoPage = userService.findAll(pageable);
        return ResponseEntity.ok(dtoPage);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserCreateDTO createDTO){
       UserDTO dto = userService.create(createDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{userId}")
    public ResponseEntity<UserDTO> update(@RequestBody @Valid UserPatchDTO patchDTO, @PathVariable UUID userId){
        UserDTO dto = userService.update(patchDTO, userId);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{userId}/toggle-active")
    public ResponseEntity<UserDTO> toggleActive(@PathVariable UUID userId){
        return ResponseEntity.ok(userService.toggleActive(userId));
    }

}
