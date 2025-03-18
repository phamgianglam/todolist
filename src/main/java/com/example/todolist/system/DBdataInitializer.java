package com.example.todolist.system;

import com.example.todolist.helper.Status;
import com.example.todolist.profile.Profile;
import com.example.todolist.task.Task;
import com.example.todolist.task.TaskRepository;
import com.example.todolist.profile.ProfileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class DBdataInitializer implements CommandLineRunner {

    private final ProfileRepository profileRepository;

    private final TaskRepository taskRepository;

    public DBdataInitializer(ProfileRepository profileRepository, TaskRepository taskRepository) {
        this.profileRepository = profileRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
