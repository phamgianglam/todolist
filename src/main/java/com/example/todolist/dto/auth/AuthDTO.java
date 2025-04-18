package com.example.todolist.dto.auth;

import jakarta.annotation.Nonnull;

public record AuthDTO(@Nonnull String username, @Nonnull String password) {}
