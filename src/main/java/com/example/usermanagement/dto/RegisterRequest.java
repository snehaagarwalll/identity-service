package com.example.usermanagement.dto;

import jakarta.validation.constraints.*;
        import lombok.*;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 5, message = "Password must be at least 5 characters")
    private String password;

    @NotBlank(message = "Role is required")
    private String role;
}

