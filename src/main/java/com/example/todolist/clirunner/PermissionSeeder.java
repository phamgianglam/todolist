package com.example.todolist.clirunner;

import com.example.todolist.model.Permission;
import com.example.todolist.repository.PermissionRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"prod", "dev"})
public class PermissionSeeder implements CommandLineRunner {
  private final PermissionRepository permissionRepository;

  public PermissionSeeder(PermissionRepository permissionRepository) {
    this.permissionRepository = permissionRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    List<String> defaultPermissions =
        List.of(
            "PROFILE_READ_OWN",
            "PROFILE_WRITE_OWN",
            "TASK_READ_OWN",
            "TASK_WRITE_OWN",
            "TAG_WRITE_OWN",
            "TAG_READ_OWN");

    for (String perm : defaultPermissions) {
      permissionRepository
          .findByName(perm)
          .orElseGet(() -> permissionRepository.save(new Permission(perm)));
    }
  }
}
