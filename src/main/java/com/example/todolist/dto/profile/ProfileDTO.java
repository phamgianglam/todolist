package com.example.todolist.dto.profile;

public record ProfileDTO(
    long id, String username, String email, Integer numberOfTasks, String imagePath) {}
