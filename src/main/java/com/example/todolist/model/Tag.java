package com.example.todolist.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Tag {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  Long id;

  private String name;

  @ManyToMany(mappedBy = "tags")
  Set<Task> tasks = new HashSet<>();
}
