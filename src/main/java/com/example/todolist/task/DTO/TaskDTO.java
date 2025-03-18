package com.example.todolist.task.DTO;

import com.example.todolist.profile.dto.ProfileDTO;

public record TaskDTO(long id, String title, String description, ProfileDTO owner){

}