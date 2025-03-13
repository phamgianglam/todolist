package com.example.todolist.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum Status {
    PENDING("Pending"),
    DONE("Done");

    private String value;

    Status(String value) {
        this.value = value;
    }
}