package com.example.todolist.model;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "profile_user")
public class Profile implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
  @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
  private long id;

  @Column(nullable = false)
  private String username;
  @Column(nullable = false)
  private String password;

  @Column(nullable = true)
  private String email;

  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE},
      mappedBy = "owner")
  private List<Task> tasks = new ArrayList<>();

  @Column(nullable = true)
  private String imagePath;
  public void addTasks(Task task) {
    task.setOwner(this);
    this.tasks.add(task);
  }

  public Profile(String username, String password, String email) {
    this(username, password, email, Role.USER);
  }

  public Profile(String username, String password, String email, Role role) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.role = role;
  }
}

enum Role {
  ADMIN,
  USER
}
