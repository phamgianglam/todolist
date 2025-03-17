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
    private long id;

    private String username;

    private String password;

    private String email;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "owner")
    private List<Task> tasks = new ArrayList<>();

    public void addTasks(Task task) {
        task.setOwner(this);
        this.tasks.add(task);
    }
}
