package com.example.todolist.converter.task;

import com.example.todolist.model.Profile;
import com.example.todolist.util.Exceptions;
import com.example.todolist.repository.ProfileRepository;
import com.example.todolist.model.Task;
import com.example.todolist.dto.task.TaskRequestDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static com.example.todolist.util.Helper.convertISoStringToZoneDateTime;

@Component
public class TaskRequestDTOToTaskConverter implements Converter<TaskRequestDTO, Task> {

    private ProfileRepository profileRepository;

    public TaskRequestDTOToTaskConverter(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Task convert(TaskRequestDTO taskRequestDTO) {
        Task task = new Task();
        Profile profile = null;
        if (taskRequestDTO.ownerId() != null) {
            profile = this.profileRepository.findById(taskRequestDTO.ownerId())
                    .orElseThrow(() -> new Exceptions.ObjectNotFoundException("profile"));
        }

        task.setTitle(taskRequestDTO.title());
        task.setDueDate(convertISoStringToZoneDateTime(taskRequestDTO.dueDate()));
        task.setStatus(taskRequestDTO.status());
        task.setDescription(taskRequestDTO.description());
        task.setOwner(profile);

        return task;
    }
}
