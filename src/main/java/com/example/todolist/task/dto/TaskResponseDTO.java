package com.example.todolist.task.dto;

import com.example.todolist.util.Status;
import com.example.todolist.profile.dto.ProfileDTO;

public record TaskResponseDTO(long id,
                              String title,
                              String description,
                              String dueDate,
                              Status status,
                              ProfileDTO owner){

}