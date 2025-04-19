package com.example.todolist.profile.dto;

public record ProfileUpdateDTO(
                         String username,
                         String email,
                         Integer numberOfTasks) {
}
