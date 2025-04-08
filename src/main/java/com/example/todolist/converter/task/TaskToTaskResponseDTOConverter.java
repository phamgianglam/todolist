package com.example.todolist.converter.task;

import static com.example.todolist.util.Helper.convertDateTimeObjectToIsoString;

import com.example.todolist.converter.profile.ProfileToProfileDTOConverter;
import com.example.todolist.dto.task.TaskResponseDTO;
import com.example.todolist.model.Task;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskToTaskResponseDTOConverter implements Converter<Task, TaskResponseDTO> {

  private final ProfileToProfileDTOConverter profileToProfileDTOConverter;

  public TaskToTaskResponseDTOConverter(ProfileToProfileDTOConverter profileToProfileDTOConverter) {
    this.profileToProfileDTOConverter = profileToProfileDTOConverter;
  }

  @Override
  public TaskResponseDTO convert(Task source) {
    TaskResponseDTO taskResponseDTO =
        new TaskResponseDTO(
            source.getId(),
            source.getTitle(),
            source.getDescription(),
            convertDateTimeObjectToIsoString(source.getDueDate()),
            source.getStatus(),
            source.getOwner() != null
                ? this.profileToProfileDTOConverter.convert(source.getOwner())
                : null);

    return taskResponseDTO;
  }
}
