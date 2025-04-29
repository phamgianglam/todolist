package com.example.todolist.containerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.todolist.model.Profile;
import com.example.todolist.model.Tag;
import com.example.todolist.model.Task;
import com.example.todolist.repository.ProfileRepository;
import com.example.todolist.repository.TagRepository;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.util.Status;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class TaskTest extends IntegrationTestSetup {

  @Autowired private TaskRepository taskRepository;

  @Autowired private ProfileRepository profileRepository;

  @Autowired private TagRepository tagRepository;

  @Test
  void testCreateTask() {
    // Implement your test logic here

    var task =
        Task.builder()
            .title("Test Task")
            .description("This is a test task")
            .owner(null)
            .dueDate(ZonedDateTime.now())
            .status(Status.DONE)
            .build();

    var savedTask = taskRepository.save(task);
    assertEquals("Test Task", savedTask.getTitle());
  }

  @Test
  void testFetchTask() {
    // Implement your test logic here

    var task =
        Task.builder()
            .title("Test Task")
            .description("This is a test task")
            .owner(null)
            .dueDate(ZonedDateTime.now())
            .status(Status.DONE)
            .build();

    var savedTask = taskRepository.save(task);
    var fetchedTask = taskRepository.findById(savedTask.getId()).orElse(null);
    assertEquals(savedTask.getTitle(), fetchedTask.getTitle());
  }

  @Test
  void testFetchTaskNotExist() {
    var task =
        Task.builder()
            .title("Test Task")
            .description("This is a test task")
            .owner(null)
            .dueDate(ZonedDateTime.now())
            .status(Status.DONE)
            .build();

    var savedTask = taskRepository.save(task);
    var fetchedTask = taskRepository.findById(savedTask.getId() + 1).orElse(null);
    assertNull(fetchedTask);
  }

  @Test
  void testFetchTasks() {
    var tasks = taskRepository.findAll();
    assertEquals(5, tasks.size());
  }

  @Test
  void testDeleteTask() {
    var task =
        Task.builder()
            .title("Test Task")
            .description("This is a test task")
            .owner(null)
            .dueDate(ZonedDateTime.now())
            .status(Status.DONE)
            .build();

    var savedTask = taskRepository.save(task);
    taskRepository.deleteById(savedTask.getId());
    var fetchedTask = taskRepository.findById(savedTask.getId()).orElse(null);
    assertNull(fetchedTask);
  }

  @Test
  void testUpdateTask() {
    var task =
        Task.builder()
            .title("Test Task")
            .description("This is a test task")
            .owner(null)
            .dueDate(ZonedDateTime.now())
            .status(Status.DONE)
            .build();

    var savedTask = taskRepository.save(task);
    savedTask.setTitle("Updated Task");
    var updatedTask = taskRepository.save(savedTask);
    assertEquals("Updated Task", updatedTask.getTitle());
  }

  @Test
  void testFetchTaskByOwner() {
    var profile = Profile.builder().username("testuser").password("").email("abc@abc").build();
    profile = profileRepository.save(profile);
    var task =
        Task.builder()
            .title("Test Task")
            .description("This is a test task")
            .owner(profile)
            .dueDate(ZonedDateTime.now())
            .status(Status.DONE)
            .build();

    var savedTask = taskRepository.save(task);
    var tasks = taskRepository.findByOwnerUsername("testuser");
    assertEquals(1, tasks.size());
    assertEquals(savedTask.getTitle(), tasks.getFirst().getTitle());
  }

  @Test
  void testAddTagAndRemoveTag() {
    var tag = Tag.builder().name("DONE").build();

    var savedTag = tagRepository.save(tag);
    var task =
        Task.builder()
            .title("Test Task")
            .description("This is a test task")
            .owner(null)
            .dueDate(ZonedDateTime.now())
            .status(Status.DONE)
            .build();

    var savedTask = taskRepository.save(task);

    savedTask.addTagToTask(savedTag);
    assertEquals(1, savedTask.getTags().size());

    savedTask.removeTagFromTasks(savedTag);
    assertEquals(0, savedTask.getTags().size());
  }
}
