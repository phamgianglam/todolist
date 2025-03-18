package com.example.todolist.profile;

public class ProfileException {
    public static class UserNotFoundException extends RuntimeException{
        public UserNotFoundException(Long id) {
            super("Could not find user with Id " + String.valueOf(id));
        }
    }

    public static class BadRequestException extends RuntimeException{
        public BadRequestException() {
            super("Bad request");
        }
    }
}
