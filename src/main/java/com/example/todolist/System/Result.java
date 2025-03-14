package com.example.todolist.System;

public class Result {
    private Object data;

    private int status;

    private String message;

    public Result(Object data, int status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }
}
