package com.example.todolist.task;

import java.util.List;
import com.example.todolist.profile.ProfileRepository;
import com.example.todolist.util.Exceptions;
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
        this.taskRepository.deleteById(taskId);
    }

    public Task findbyId(Long taskId) {
        return this.taskRepository.findById(taskId)
                .orElseThrow(() -> new Exceptions.ObjectNotFoundException(taskId, "task"));
    }

    public List<Task> findAll() {
        return this.taskRepository.findAll();
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
