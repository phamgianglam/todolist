package com.example.todolist.user;

import com.example.todolist.task.Task;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class User implements Serializable {

    @Id
    private long id;

    private String username;

    private String password;

    private String email;

    @OneToMany(cascade = {}, mappedBy = "owner")
    private List<Task> tasks;
}
