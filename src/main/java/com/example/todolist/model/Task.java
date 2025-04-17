package com.example.todolist.model;

import com.example.todolist.util.Status;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Task implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  private String title;

  private String description;

  private Status status;

  private ZonedDateTime dueDate;

  @ManyToMany
  @JoinTable(
      name = "tasks_tags",
      joinColumns = @JoinColumn(name = "task_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  Set<Tag> tags = new HashSet<>();

  @ManyToOne private Profile owner;

  public void addTagToTask(Tag tag) {
    this.tags.add(tag);
    tag.getTasks().add(this);
  }

  public void removeTagFromTasks(Tag tag) {
    this.tags.remove(tag);
    tag.getTasks().remove(this);
  }
}
