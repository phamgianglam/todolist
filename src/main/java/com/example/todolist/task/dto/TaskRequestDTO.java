package com.example.todolist.task.dto;

import com.example.todolist.helper.Status;
import com.example.todolist.profile.dto.ProfileDTO;

public record TaskRequestDTO(
                             String title,
                             String description,
                             Status status,
                             String dueDate,
                             long ownerId) {
}
