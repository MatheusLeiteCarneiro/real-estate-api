package com.mlcdev.realestate.dto;

import com.mlcdev.realestate.validation.StrongPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserCreateDTO {

    @NotBlank(message = "Username can't be blank")
    @Size(min = 3, message = "The username must have more than 3 characters")
    private String username;

    @NotBlank(message = "Password required")
    @StrongPassword
    private String password;

}
