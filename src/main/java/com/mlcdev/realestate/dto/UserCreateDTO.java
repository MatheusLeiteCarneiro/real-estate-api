package com.mlcdev.realestate.dto;

import com.mlcdev.realestate.validation.StrongPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Schema(name = "UserCreateRequest", description = "Request body used to create a user.")
public class UserCreateDTO {

    @NotBlank(message = "Username can't be blank")
    @Size(min = 3, message = "The username must have at least 3 characters")
    @Schema(description = "Unique username for the user account.", example = "broker.user", minLength = 3, requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotBlank(message = "Password required")
    @StrongPassword
    @Schema(description = "User password with uppercase, lowercase, number, and special character.", example = "Str0ng@Password", format = "password", minLength = 8, requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

}
