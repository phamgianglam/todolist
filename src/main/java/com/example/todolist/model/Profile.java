package com.example.todolist.model;

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
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
  private long id;

  private String username;

  private String password;

  private String email;

  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE},
      mappedBy = "owner")
  private List<Task> tasks = new ArrayList<>();

  public void addTasks(Task task) {
    task.setOwner(this);
    this.tasks.add(task);
  }

  public Profile(String username, String password, String email, List<Task> tasks) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.tasks = tasks;
  }
}

enum Role {
  ADMIN,
  USER
}
