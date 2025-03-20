package com.example.todolist.task;

import java.util.List;
import com.example.todolist.system.Result;
import com.example.todolist.task.converter.TaskRequestDTOToTaskConverter;
import com.example.todolist.task.converter.TaskToTaskResponseDTOConverter;
import com.example.todolist.task.dto.TaskRequestDTO;
import com.example.todolist.task.dto.TaskResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
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
    public Result createTask(@RequestBody TaskRequestDTO taskRequestDTO) {
        Task task = this.taskRequestDTOToTaskConverter.convert(taskRequestDTO);

        if (task.getOwner() != null) {
            task.getOwner().addTasks(task);
        }
        task = this.taskService.createTask(task);

        TaskResponseDTO responseDTO = this.taskToTaskResponseDTOConverter.convert(task);

        return new Result(responseDTO, 201, "Successfully add task");
    }

    @GetMapping("/api/v1/tasks/{taskId}")
    public Result findById(@PathVariable long taskId) {
        Task task = this.taskService.findbyId(taskId);
        TaskResponseDTO dto = this.taskToTaskResponseDTOConverter.convert(task);
        return new Result(dto, 200, "Task Found");
    }

    @GetMapping("/api/v1/tasks/")
    public Result findAll() {
        List<Task> tasks = this.taskService.findAll();
        List<TaskResponseDTO> TasksDto =
                tasks.stream().map(this.taskToTaskResponseDTOConverter::convert).toList();

        return new Result(TasksDto, 200, "Objects found");
    }

    @DeleteMapping("/api/v1/tasks/{taskId}")
    public ResponseEntity deleteTask(@PathVariable long taskId) {
        this.taskService.deleteTask(taskId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/api/v1/tasks/{taskId}")
    public Result patchTask(@PathVariable long taskId, @RequestBody TaskRequestDTO body) {
        Task task = this.taskRequestDTOToTaskConverter.convert(body);
        task = this.taskService.patchTask(task, taskId);
        TaskResponseDTO responseDto = this.taskToTaskResponseDTOConverter.convert(task);

        return new Result(responseDto, 200, "Update successfully");

    }
}
