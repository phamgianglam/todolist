package com.example.todolist.dto.profile;

import jakarta.validation.constraints.Email;

public record ProfilePartialRequestDTO(
    String username, @Email(message = "should be valid") String email) {}
