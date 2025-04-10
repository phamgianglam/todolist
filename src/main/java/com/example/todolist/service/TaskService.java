package com.example.todolist.service;

import com.example.todolist.dto.task.TaskPartialRequestDTO;
import com.example.todolist.model.Profile;
import com.example.todolist.model.Task;
import com.example.todolist.repository.ProfileRepository;
import com.example.todolist.repository.TagRepository;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.util.Exceptions;
import com.example.todolist.util.Helper;
import jakarta.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TaskService {
  private final TaskRepository taskRepository;
  private final TagRepository tagRepository;
  private final ProfileRepository profileRepository;

  public TaskService(
      TaskRepository taskRepository,
      TagRepository tagRepository,
      ProfileRepository profileRepository) {
    this.taskRepository = taskRepository;
    this.tagRepository = tagRepository;
    this.profileRepository = profileRepository;
  }

  public Task createTask(Task task) {
    return this.taskRepository.save(task);
  }

  public void deleteTask(Long taskId) {
    Task task = this.findbyId(taskId);
    this.taskRepository.delete(task);
  }

  public Task findbyId(Long taskId) {
    Task task =
        this.taskRepository
            .findById(taskId)
            .orElseThrow(() -> new Exceptions.ObjectNotFoundException("task"));
    if (Helper.isAdmin()) {
      return task;
    } else {
      String username = Helper.getCurrentUsername();
      if (username.equals(task.getOwner().getUsername())) return task;
      throw new Exceptions.ObjectNotFoundException("task");
    }
  }

  public List<Task> findAll() {
    if (Helper.isAdmin()) {
      return this.taskRepository.findAll();
    } else {
      String username = Helper.getCurrentUsername();
      return this.findAllByOwner(username);
    }
  }

  public List<Task> findAllByOwner(String username) {
    return this.taskRepository.findByOwnerUsername(username);
  }

  public Task patchTask(TaskPartialRequestDTO data, Long taskId) {
    Task task = this.findbyId(taskId);

    if (data.description() != null) task.setDescription(data.description());
    if (data.dueDate() != null) task.setDueDate(ZonedDateTime.parse(data.dueDate()));
    if (data.title() != null) task.setTitle(data.title());
    if (data.status() != null) task.setStatus(data.status());
    if (data.ownerId() != null) {
      Profile profile =
          profileRepository
              .findById(data.ownerId())
              .orElseThrow(() -> new Exceptions.ObjectNotFoundException(data.ownerId(), "Profile"));
      profile.addTasks(task);
    }
    ;
    task = this.taskRepository.save(task);

    return task;
  }

  public void addTagToTask(Long TaskId, Long TagId) {
    var tag =
        tagRepository
            .findById(TagId)
            .orElseThrow(() -> new Exceptions.ObjectNotFoundException(TagId, "Tag"));
    var task = findbyId(TaskId);
    task.addTagToTask(tag);
  }

  public void removeTagFromTask(Long TaskId, Long TagId) {
    var tag =
        tagRepository
            .findById(TagId)
            .orElseThrow(() -> new Exceptions.ObjectNotFoundException(TagId, "Tag"));
    var task = findbyId(TaskId);
    task.removeTagFromTasks(tag);
  }
}
