package com.example.todolist.task.dto;

import com.example.todolist.helper.Status;

public record TaskRequestDTO(
                             String title,
                             String description,
                             Status status,
                             String dueDate,
                             Long ownerId) {
}
