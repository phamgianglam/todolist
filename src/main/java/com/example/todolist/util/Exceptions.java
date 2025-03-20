package com.example.todolist.util;

public class Exceptions {
    public static class ObjectNotFoundException extends RuntimeException {
        public ObjectNotFoundException(Long id, String objectType) {
            super("Could not find" + objectType + "with Id " + String.valueOf(id));
        }
    }

    public static class BadRequestException extends RuntimeException {
        public BadRequestException() {
            super("Bad request");
        }
    }
}
