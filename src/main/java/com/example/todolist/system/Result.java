package com.example.todolist.system;

import lombok.*;

@Setter
@Getter
public class Result {
    private Object data;

    private int status;

    private String message;

    public Result(Object data, int status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public Result(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
