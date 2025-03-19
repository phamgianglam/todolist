package com.example.todolist.task.converter;

import com.example.todolist.task.Task;
import com.example.todolist.task.dto.TaskRequestDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskRequestDTOToTaskConverter implements Converter <TaskRequestDTO, Task> {
    @Override
    public Task convert(TaskRequestDTO taskRequestDTO){
        Task task = new Task();
        if
        return  task;
    }
}
