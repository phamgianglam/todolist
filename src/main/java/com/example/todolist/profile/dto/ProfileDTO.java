package com.example.todolist.profile.dto;

public record ProfileDTO(long id,
                         String username,
                         String email,
                         Integer numberOfTasks) {
}
