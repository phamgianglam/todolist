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
        task1.setOwner(profile);
        profile.addTasks(task1);
        profileRepository.save(profile);
    }
}
