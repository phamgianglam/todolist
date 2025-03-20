package com.example.todolist.task;

import com.example.todolist.system.Result;
import com.example.todolist.task.converter.TaskRequestDTOToTaskConverter;
import com.example.todolist.task.converter.TaskToTaskResponseDTOConverter;
import com.example.todolist.task.dto.TaskRequestDTO;
import com.example.todolist.task.dto.TaskResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
