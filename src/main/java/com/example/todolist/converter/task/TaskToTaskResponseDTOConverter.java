package com.example.todolist.converter.task;

import static com.example.todolist.util.Helper.convertDateTimeObjectToIsoString;

import com.example.todolist.dto.task.TaskResponseDTO;
import com.example.todolist.model.Task;
import lombok.NoArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class TaskToTaskResponseDTOConverter implements Converter<Task, TaskResponseDTO> {

  @Override
  public TaskResponseDTO convert(Task source) {
    return new TaskResponseDTO(
        source.getId(),
        source.getTitle(),
        source.getDescription(),
        convertDateTimeObjectToIsoString(source.getDueDate()),
        source.getStatus(),
        source.getOwner() != null ? source.getOwner().getUsername() : null);
  }
}
