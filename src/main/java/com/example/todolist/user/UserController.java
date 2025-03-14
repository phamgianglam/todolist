package com.example.todolist.user;

import com.example.todolist.System.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("api/v1/user/{userId}")
    public Result findUseById(@PathVariable long userID) {

        return null;
    }
}
