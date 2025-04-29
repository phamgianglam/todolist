package com.example.todolist.model;

import com.example.todolist.util.Status;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
  @SequenceGenerator(name = "task_seq", sequenceName = "task_sequence", allocationSize = 1)
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
  @Fetch(FetchMode.JOIN)
  private final Set<Tag> tags = new HashSet<>();

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
