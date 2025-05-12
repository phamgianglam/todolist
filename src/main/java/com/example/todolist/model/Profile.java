package com.example.todolist.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
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

  @Column(nullable = false)
  private boolean enabled;

  @Column(nullable = false)
  private boolean accountNonBlocked;

  @Column(nullable = false)
  private boolean passwordNonExpired;

  @Column(nullable = false)
  private int daysToExpirePassword;

  @Column(nullable = false)
  private ZonedDateTime lastPasswordResetDate;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "profile_permissions",
      joinColumns = @JoinColumn(name = "profile_id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id"))
  private Set<Permission> permissions = new HashSet<>();

  @OneToMany(
      cascade = {CascadeType.PERSIST, CascadeType.MERGE},
      mappedBy = "owner")
  private final List<Task> tasks = new ArrayList<>();

  @Column(nullable = true)
  private String imagePath;

  public void addTasks(Task task) {
    task.setOwner(this);
    this.tasks.add(task);
  }

  public Profile(String username, String password, String email) {
    this.username = username;
    this.password = password;
    this.email = email;
  }
}
