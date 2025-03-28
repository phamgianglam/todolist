package com.example.todolist.service;

import java.util.List;
import com.example.todolist.model.Task;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.util.Exceptions;
import com.example.todolist.util.Helper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) {
        return this.taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        Task task = this.findbyId(taskId);
        this.taskRepository.delete(task);
    }

    public Task findbyId(Long taskId) {
        Task task = this.taskRepository.findById(taskId)
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
        if (Helper.isAdmin())
        {
            return this.taskRepository.findAll();
        } else {
            String username = Helper.getCurrentUsername();
            return this.findAllByOwner(username);
        }

    }

    public List<Task> findAllByOwner(String username){
        return this.taskRepository.findByOwnerUsername(username);
    }

    public Task patchTask(Task data, Long taskId) {
        Task task = this.findbyId(taskId);

        if (data.getDescription() != null)
            task.setDescription(data.getDescription());
        if (data.getDueDate() != null)
            task.setDueDate(data.getDueDate());
        if (data.getTitle() != null)
            task.setTitle(data.getTitle());
        if (data.getStatus() != null)
            task.setStatus(data.getStatus());
        if (data.getOwner() != null)
            data.getOwner().addTasks(task);
        task = this.taskRepository.save(task);

        return task;
    }
}
