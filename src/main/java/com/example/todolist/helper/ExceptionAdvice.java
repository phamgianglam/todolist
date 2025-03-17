package com.example.todolist.helper;

import com.example.todolist.system.Result;
import com.example.todolist.profile.ProfileException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(ProfileException.UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Result handlerNotFoundException(ProfileException.UserNotFoundException ex){
        return new Result(404, ex.getMessage());
    }
}
