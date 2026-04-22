package com.mlcdev.realestate.dto;

import com.mlcdev.realestate.validation.StrongPassword;
import jakarta.validation.constraints.Size;
import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserPatchDTO {

    @Size(min = 3, message = "The username must have more than 3 characters")
    private String username;

    @StrongPassword
    private String password;

}
