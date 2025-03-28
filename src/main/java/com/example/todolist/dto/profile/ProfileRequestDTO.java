package com.example.todolist.dto.profile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ProfileRequestDTO(String username,
                                String password,
                                @Email(message = "should be valid")
                                @NotBlank(message = "Email is mandatory")
                                String email) {
}
