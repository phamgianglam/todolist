package com.example.todolist.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import com.example.todolist.dto.task.TaskPartialRequestDTO;
import com.example.todolist.dto.task.TaskRequestDTO;
import com.example.todolist.model.Profile;
import com.example.todolist.model.Task;
import com.example.todolist.service.TaskService;
import com.example.todolist.util.Exceptions;
import com.example.todolist.util.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class TaskControllerTest {
  @Autowired MockMvc mockMvc;

  @MockitoBean TaskService taskService;

  List<Task> tasks = new ArrayList<>();

  private static final ObjectMapper mapper = new ObjectMapper();

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
  void testFindTaskById() throws Exception {
    given(taskService.findbyId(1L)).willReturn(tasks.getFirst());
    this.mockMvc
        .perform(MockMvcRequestBuilders.get("/api/v1/tasks/1").accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
  }

  @Test
  void testFindTaskByIdNotFound() throws Exception {
    given(taskService.findbyId(1L))
        .willThrow(new Exceptions.ObjectNotFoundException("Task not found"));
    this.mockMvc
        .perform(MockMvcRequestBuilders.get("/api/v1/tasks/1").accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  void testFindAllTasks() throws Exception {
    given(this.taskService.findAll()).willReturn(tasks);
    this.mockMvc
        .perform(MockMvcRequestBuilders.get("/api/v1/tasks/").accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void testCreateTask() throws Exception {
    given(this.taskService.createTask(any(Task.class))).willReturn(tasks.getFirst());

    var taskDTO =
        TaskRequestDTO.builder()
            .title("Clean")
            .status(Status.DONE)
            .description("Clean the house")
            .dueDate("2026-02-20T20:19:04Z")
            .build();

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/v1/tasks/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(taskDTO)))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  void testCreateTaskBadDueDAte() throws Exception {
    var taskDTO =
        TaskRequestDTO.builder()
            .title("Clean")
            .status(Status.DONE)
            .description("Clean the house")
            .dueDate("notdate")
            .build();

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/v1/tasks/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(taskDTO)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  public void testDeleteById() throws Exception {
    doNothing().when(taskService).deleteTask(1L);
    mockMvc
        .perform(MockMvcRequestBuilders.delete("/api/v1/tasks/1"))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  public void patchTaskSuccessfully() throws Exception {
    var dtoObject = TaskPartialRequestDTO.builder().title("go to school").build();
    var task = tasks.getFirst();
    task.setTitle("go to school");
    given(taskService.patchTask(dtoObject, 1L)).willReturn(task);

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.patch("/api/v1/tasks/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dtoObject)))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }
}
