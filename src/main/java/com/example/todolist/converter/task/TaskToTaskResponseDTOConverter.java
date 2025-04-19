package com.example.todolist.converter.task;

import com.example.todolist.dto.task.TaskResponseDTO;
import com.example.todolist.model.Task;
import com.example.todolist.util.Helper;
import lombok.NoArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class TaskToTaskResponseDTOConverter implements Converter<Task, TaskResponseDTO> {
  private Helper helper;

  public TaskToTaskResponseDTOConverter(Helper helper) {
    this.helper = helper;
  }

  @Override
  public TaskResponseDTO convert(Task source) {
    return new TaskResponseDTO(
        source.getId(),
        source.getTitle(),
        source.getDescription(),
        helper.convertDateTimeObjectToIsoString(source.getDueDate()),
        source.getStatus(),
        source.getOwner() != null ? source.getOwner().getUsername() : null);
  }
}
