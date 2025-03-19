package com.example.todolist.task.dto;

import com.example.todolist.helper.Status;
import com.example.todolist.profile.dto.ProfileDTO;

public record TaskResponseDTO(long id,
                              String title,
                              String description,
                              String dueDate,
                              Status status,
                              ProfileDTO owner){

}