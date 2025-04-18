package com.example.todolist.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

  @ExceptionHandler(Exceptions.ObjectNotFoundException.class)
  ResponseEntity<String> handlerNotFoundException(Exceptions.ObjectNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Object not found");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD REQUEST");
  }
}
