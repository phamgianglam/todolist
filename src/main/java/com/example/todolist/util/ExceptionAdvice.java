package com.example.todolist.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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
    List<ObjectError> errors = ex.getBindingResult().getAllErrors();
    Map<String, String> map = new HashMap<>(errors.size());
    errors.forEach(
        (error) -> {
          String key = ((FieldError) error).getField();
          String val = error.getDefaultMessage();
          map.put(key, val);
        });

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD REQUEST");
  }
}
