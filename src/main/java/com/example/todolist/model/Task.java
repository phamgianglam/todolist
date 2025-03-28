package com.example.todolist.model;

import com.example.todolist.util.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Data
@NoArgsConstructor
public class Task implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    private String title;

    private String description;

    private Status status;

    private ZonedDateTime dueDate;

    @ManyToOne
    private Profile owner;
}
