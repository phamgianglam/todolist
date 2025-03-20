package com.example.todolist.task.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static com.example.todolist.helper.Helper.convertDateTimeObjectToIsoString;

import com.example.todolist.profile.converter.ProfileToProfileDTOConverter;
import com.example.todolist.task.dto.TaskResponseDTO;
import com.example.todolist.task.Task;

@Component
public class TaskToTaskResponseDTOConverter implements Converter<Task, TaskResponseDTO> {

    private final ProfileToProfileDTOConverter profileToProfileDTOConverter;

    public TaskToTaskResponseDTOConverter(
            ProfileToProfileDTOConverter profileToProfileDTOConverter) {
        this.profileToProfileDTOConverter = profileToProfileDTOConverter;
    }

    @Override
    public TaskResponseDTO convert(Task source) {
        TaskResponseDTO taskResponseDTO =
                new TaskResponseDTO(source.getId(), source.getTitle(), source.getDescription(),
                        convertDateTimeObjectToIsoString(source.getDueDate()), source.getStatus(),
                        source.getOwner() != null
                                ? this.profileToProfileDTOConverter.convert(source.getOwner())
                                : null);

        return taskResponseDTO;
    }
}
