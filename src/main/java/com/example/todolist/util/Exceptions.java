package com.example.todolist.util;

import org.springframework.lang.Nullable;

public class Exceptions {
  public static class ObjectNotFoundException extends RuntimeException {
    // Constructor with id
    public ObjectNotFoundException(Long id, String objectType) {
      super("Could not find " + objectType + " with Id " + id);
    }

    // Constructor without id
    public ObjectNotFoundException(String objectType) {
      super("Could not find " + objectType);
    }
  }

  public static class BadRequestException extends RuntimeException {
    public BadRequestException(@Nullable String message) {
      super(message != null ? message : "Bad request");
    }
  }
}
