package com.example.todolist.dto.task;

import com.example.todolist.util.Status;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record TaskRequestDTO(
                             @NotNull
                             String title,
                             String description,
                             @NotNull
                             Status status,
                             @Pattern(regexp = "^\\d{4}-(?:0[1-9]|1[0-2])-(?:0[1-9]|[12]\\d|3[01])T(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d(?:Z|[+-](?:[01]\\d|2[0-3]):[0-5]\\d)?$",
                                     message = "dueDate must be in ISO 8601 format (e.g., '2023-05-20T10:15:30Z' or '2023-05-20T10:15:30+01:00')")
                             @NotNull
                             String dueDate,
                             Long ownerId) {
}
