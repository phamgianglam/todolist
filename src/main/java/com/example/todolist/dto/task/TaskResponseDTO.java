package com.example.todolist.dto.task;

import com.example.todolist.dto.profile.ProfileDTO;
import com.example.todolist.util.Status;

public record TaskResponseDTO(
    long id, String title, String description, String dueDate, Status status, ProfileDTO owner) {}
