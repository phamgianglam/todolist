package com.example.todolist.user;

public class Exception {
    public static class UserNotFoundException extends RuntimeException{
        public UserNotFoundException(Long id) {
            super("Could not find user with Id " + String.valueOf(id));
        }
    }
}
