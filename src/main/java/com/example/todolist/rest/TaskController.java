package com.example.todolist.rest;

import java.util.List;
import com.example.todolist.model.Task;
import com.example.todolist.service.TaskService;
import com.example.todolist.converter.task.TaskRequestDTOToTaskConverter;
import com.example.todolist.converter.task.TaskToTaskResponseDTOConverter;
import com.example.todolist.dto.task.TaskRequestDTO;
import com.example.todolist.dto.task.TaskResponseDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name = "BearerAuth")
public class TaskController {
    private final TaskService taskService;

    private final TaskRequestDTOToTaskConverter taskRequestDTOToTaskConverter;

    private final TaskToTaskResponseDTOConverter taskToTaskResponseDTOConverter;

    public TaskController(TaskService taskService,
            TaskRequestDTOToTaskConverter taskRequestDTOToTaskConverter,
            TaskToTaskResponseDTOConverter taskToTaskResponseDTOConverter) {
        this.taskService = taskService;
        this.taskRequestDTOToTaskConverter = taskRequestDTOToTaskConverter;
        this.taskToTaskResponseDTOConverter = taskToTaskResponseDTOConverter;
    }

    @PostMapping("/api/v1/tasks/")
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskRequestDTO taskRequestDTO) {
        Task task = this.taskRequestDTOToTaskConverter.convert(taskRequestDTO);

        if (task.getOwner() != null) {
            task.getOwner().addTasks(task);
        }
        task = this.taskService.createTask(task);

        TaskResponseDTO responseDTO = this.taskToTaskResponseDTOConverter.convert(task);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/api/v1/tasks/{taskId}")
    public ResponseEntity<TaskResponseDTO> findById(@PathVariable long taskId) {
        Task task = this.taskService.findbyId(taskId);
        TaskResponseDTO dto = this.taskToTaskResponseDTOConverter.convert(task);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/api/v1/tasks/")
    public ResponseEntity<List<TaskResponseDTO>> findAll() {
        List<Task> tasks = this.taskService.findAll();
        List<TaskResponseDTO> TasksDtos =
                tasks.stream().map(this.taskToTaskResponseDTOConverter::convert).toList();

        return ResponseEntity.ok(TasksDtos);
    }

    @DeleteMapping("/api/v1/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable long taskId) {
        this.taskService.deleteTask(taskId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/api/v1/tasks/{taskId}")
    public ResponseEntity<Void> patchTask(@PathVariable long taskId, @RequestBody TaskRequestDTO body) {
        Task task = this.taskRequestDTOToTaskConverter.convert(body);
        if (task != null){
            this.taskService.patchTask(task, taskId);
        }

        return ResponseEntity.noContent().build();
    }
}
