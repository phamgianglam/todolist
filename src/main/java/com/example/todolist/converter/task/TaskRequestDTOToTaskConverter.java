package com.example.todolist.converter.task;

import com.example.todolist.dto.task.TaskRequestDTO;
import com.example.todolist.model.Profile;
import com.example.todolist.model.Task;
import com.example.todolist.repository.ProfileRepository;
import com.example.todolist.util.Exceptions;
import com.example.todolist.util.Helper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskRequestDTOToTaskConverter implements Converter<TaskRequestDTO, Task> {

  private final ProfileRepository profileRepository;
  private final Helper helper;

  // constructor inject *both* beans
  public TaskRequestDTOToTaskConverter(Helper helper, ProfileRepository profileRepository) {
    this.helper = helper;
    this.profileRepository = profileRepository;
  }

  @Override
  public Task convert(TaskRequestDTO dto) {
    Task task = new Task();

    if (dto.ownerId() != null) {
      Profile profile =
          profileRepository
              .findById(dto.ownerId())
              .orElseThrow(() -> new Exceptions.ObjectNotFoundException("profile"));
      task.setOwner(profile);
    }

    task.setTitle(dto.title());
    task.setDueDate(helper.convertIsoStringToZoneDateTime(dto.dueDate()));
    task.setStatus(dto.status());
    task.setDescription(dto.description());

    return task;
  }
}
