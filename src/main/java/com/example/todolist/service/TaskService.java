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
  private final Helper helper;

  public TaskService(
      TaskRepository taskRepository,
      TagRepository tagRepository,
      ProfileRepository profileRepository,
      Helper helper) {
    this.taskRepository = taskRepository;
    this.tagRepository = tagRepository;
    this.profileRepository = profileRepository;
    this.helper = helper;
  }

  public Task createTask(Task task) {
    return this.taskRepository.save(task);
  }

  public void deleteTask(Long taskId) {
    this.taskRepository.deleteById(taskId);
  }

  public Task findbyId(Long taskId) {
    Task task =
        this.taskRepository
            .findById(taskId)
            .orElseThrow(() -> new Exceptions.ObjectNotFoundException("task"));
    if (helper.isAdmin()) {
      return task;
    } else {
      String username = helper.getCurrentUserName();
        assert username != null;
        if (username.equals(task.getOwner().getUsername())) return task;
      throw new Exceptions.ObjectNotFoundException("task");
    }
  }

  public List<Task> findAll() {
    if (helper.isAdmin()) {
      return this.taskRepository.findAll();
    } else {
      String username = helper.getCurrentUserName();
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
    task = this.taskRepository.save(task);

    return task;
  }

  public void addTagToTask(Long taskId, Long tagId) {
    var tag =
        tagRepository
            .findById(tagId)
            .orElseThrow(() -> new Exceptions.ObjectNotFoundException(tagId, "Tag"));
    var task = findbyId(taskId);
    task.addTagToTask(tag);
  }

  public void removeTagFromTask(long taskId, Long tagId) {
    var tag =
        tagRepository
            .findById(tagId)
            .orElseThrow(() -> new Exceptions.ObjectNotFoundException(tagId, "Tag"));
    var task = findbyId(taskId);
    task.removeTagFromTasks(tag);
  }
}
