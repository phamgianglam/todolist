package com.example.todolist.dto.task;

import com.example.todolist.util.Status;
import io.swagger.v3.oas.annotations.media.Schema;

public record TaskResponseDTO(
    @Schema(example = "1") long id,
    @Schema(example = "Clean the house") String title,
    @Schema(example = "Clean the house before leaving the house for school") String description,
    @Schema(example = "2026-02-20T20:19:04Z") String dueDate,
    @Schema(example = "PENDING") Status status,
    @Schema(example = "John") String ownerName) {}
