package com.example.todolist.task.converter;

import com.example.todolist.profile.converter.ProfileToProfileDTOConverter;
import com.example.todolist.task.DTO.TaskDTO;
import com.example.todolist.task.Task;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskConverter implements Converter<Task, TaskDTO> {

    private final ProfileToProfileDTOConverter profileToProfileDTOConverter;

    public TaskConverter(ProfileToProfileDTOConverter profileToProfileDTOConverter) {
        this.profileToProfileDTOConverter = profileToProfileDTOConverter;
    }

    @Override
    public TaskDTO convert(Task source) {
        TaskDTO taskDTO = new TaskDTO(source.getId(),
                                      source.getTitle(),
                                      source.getDescription(),
                                      source.getOwner() != null ?
                                              this.profileToProfileDTOConverter.convert(source.getOwner()):null);

        return taskDTO;
    }
}
