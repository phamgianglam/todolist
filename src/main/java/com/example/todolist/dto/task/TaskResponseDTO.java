package com.example.todolist.dto.task;

import com.example.todolist.util.Status;
import com.example.todolist.dto.profile.ProfileDTO;

public record TaskResponseDTO(long id,
                              String title,
                              String description,
                              String dueDate,
                              Status status,
                              ProfileDTO owner){

}