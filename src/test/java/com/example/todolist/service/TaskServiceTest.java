package com.example.todolist.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.example.todolist.dto.task.TaskPartialRequestDTO;
import com.example.todolist.model.Profile;
import com.example.todolist.model.Task;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.util.Exceptions;
import com.example.todolist.util.Status;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
  @Mock TaskRepository taskRepository;

  @InjectMocks TaskService taskService;

  private final List<Task> tasks = new ArrayList<>();

  @BeforeEach
  void setUp() {
    Profile profile = new Profile();
    profile.setId(1L);
    profile.setUsername("JohnDoe");
    profile.setEmail("JohnDoe@steve.com");
    profile.setPassword("randompassword");

    Task task1 = new Task();
    task1.setId(1L);
    task1.setTitle("Clean");
    task1.setDescription("Clean the house");
    task1.setStatus(Status.DONE);
    task1.setDueDate(ZonedDateTime.now());

    profile.addTasks(task1);

    tasks.add(task1);
  }

  @AfterEach
  void tearDown() {}

  @Test
  void testFindByIdSuccess() {
    given(taskRepository.findById(1L)).willReturn(Optional.of(tasks.getFirst()));

    Task task = taskService.findbyId(1L);
    assertEquals(task.getId(), tasks.getFirst().getId());
  }

  @Test
  void testFindByIdNotFound() {
    given(taskRepository.findById(1L)).willReturn(Optional.empty());

    Exception exception =
        assertThrows(
            Exceptions.ObjectNotFoundException.class,
            () -> {
              taskService.findbyId(1L);
            });
  }

  @Test
  void testFindAllSuccess() {
    given(taskRepository.findAll()).willReturn(tasks);

    List<Task> result = taskService.findAll();
    assertEquals(result.getFirst().getId(), tasks.getFirst().getId());
  }

  @Test
  void testCreateTasks() {
    given(taskRepository.save(any(Task.class))).willReturn(tasks.getFirst());
    Task task = taskService.createTask(tasks.getFirst());

    assertEquals(task.getId(), tasks.getFirst().getId());
  }

  @Test
  void testCreateTaskExisted() {
    given(taskRepository.save(any(Task.class)))
        .willThrow(new DataIntegrityViolationException("Task already exists"));

    assertThrows(
        DataIntegrityViolationException.class, () -> taskService.createTask(tasks.getFirst()));
  }

  @Test
  void testUpdateTask() {
    var oldItem = tasks.getFirst();
    given(taskRepository.findById(1L)).willReturn(Optional.of(oldItem));
    var record = new Task();
    record.setId(oldItem.getId());
    record.setDescription(oldItem.getDescription());
    record.setTitle(oldItem.getTitle());
    record.setOwner(oldItem.getOwner());
    record.setTags(oldItem.getTags());
    record.setDueDate(oldItem.getDueDate());
    record.setStatus(Status.PENDING);

    given(taskRepository.save(oldItem)).willReturn(record);
    var dto = new TaskPartialRequestDTO(null, null, Status.PENDING, null, null);
    var result = this.taskService.patchTask(dto, 1L);

    assertEquals(result.getId(), record.getId());
    assertEquals(result.getStatus(), record.getStatus());
  }

  @Test
  void testUpdateTaskNotFound() {
    given(taskRepository.findById(1L)).willReturn(Optional.empty());

    var dto = new TaskPartialRequestDTO(null, null, Status.PENDING, null, null);
    assertThrows(
        Exceptions.ObjectNotFoundException.class, () -> this.taskService.patchTask(dto, 1L));
  }

  @Test
  void testDeleteTask() {
    taskService.deleteTask(1L);
    verify(taskRepository).deleteById(1L);
  }
}
