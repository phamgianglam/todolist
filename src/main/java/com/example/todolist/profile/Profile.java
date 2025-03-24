package com.example.todolist.profile;

import com.example.todolist.task.Task;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Profile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "owner")
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
