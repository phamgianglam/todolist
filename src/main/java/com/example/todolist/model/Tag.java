package com.example.todolist.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString(exclude = "tasks")
@NoArgsConstructor
public class Tag implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  Long id;

  private String name;

  @ManyToMany(mappedBy = "tags")
  private final Set<Task> tasks = new HashSet<>();
}
