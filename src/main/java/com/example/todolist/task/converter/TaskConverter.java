package com.example.todolist.task.converter;

import com.example.todolist.profile.converter.ProfileConverter;
import com.example.todolist.task.DTO.TaskDTO;
import com.example.todolist.task.Task;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskConverter implements Converter<Task, TaskDTO> {

    private final ProfileConverter profileConverter;

    public TaskConverter(ProfileConverter profileConverter) {
        this.profileConverter = profileConverter;
    }

    @Override
    public TaskDTO convert(Task source) {
        TaskDTO taskDTO = new TaskDTO(source.getId(),
                                      source.getTitle(),
                                      source.getDescription(),
                                      source.getOwner() != null ?
                                              this.profileConverter.convert(source.getOwner()):null);

        return taskDTO;
    }
}
