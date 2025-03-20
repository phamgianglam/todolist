package com.example.todolist.task;

import com.example.todolist.util.Status;
import com.example.todolist.profile.Profile;
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
