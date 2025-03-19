package com.example.todolist.task.converter;

import com.example.todolist.profile.Profile;
import com.example.todolist.profile.ProfileException;
import com.example.todolist.profile.ProfileRepository;
import com.example.todolist.task.Task;
import com.example.todolist.task.dto.TaskRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static com.example.todolist.helper.Helper.convertISoStringToZoneDateTime;

@Component
public class TaskRequestDTOToTaskConverter implements Converter <TaskRequestDTO, Task> {

    private ProfileRepository profileRepository;

    public TaskRequestDTOToTaskConverter(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Task convert(TaskRequestDTO taskRequestDTO){
        Task task = new Task();
        Profile profile = this.profileRepository
                .findById(taskRequestDTO.ownerId())
                .orElseThrow(() -> new ProfileException.UserNotFoundException(taskRequestDTO.ownerId()));
        task.setTitle(taskRequestDTO.title());
        task.setDueDate(convertISoStringToZoneDateTime(taskRequestDTO.dueDate()));
        task.setStatus(taskRequestDTO.status());
        task.setDescription(taskRequestDTO.description());
        task.setOwner(profile);

        return  task;
    }
}
