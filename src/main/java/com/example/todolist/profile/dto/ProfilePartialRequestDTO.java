package com.example.todolist.profile.dto;

import jakarta.validation.constraints.Email;

public record ProfilePartialRequestDTO(String username,
                                       @Email(message = "should be valid")
                                       String email) {
}
