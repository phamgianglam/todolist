package com.example.todolist.dto.task;

import com.example.todolist.util.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record TaskPartialRequestDTO(
    @Schema(example = "Clean the house") String title,
    @Schema(example = "Clean the house before leaving the house for school") String description,
    @Schema(example = "PENDING") Status status, @Pattern(
        regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])T([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d(Z|[+-]([01]\\d|2[0-3]):[0-5]\\d)$",
        message = "dueDate must be in ISO 8601 format (e.g., '2023-05-20T10:15:30Z' or '2023-05-20T10:15:30+01:00')")
    @Schema(example = "2026-02-20T20:19:04Z")
    String dueDate,
    @Schema(example = "1") Long ownerId) {}
