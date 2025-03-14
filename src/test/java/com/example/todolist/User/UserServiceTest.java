package com.example.todolist.User;

import com.example.todolist.helper.Status;
import com.example.todolist.task.Task;
import com.example.todolist.user.Exception.*;
import com.example.todolist.user.User;
import com.example.todolist.user.UserRepository;
import com.example.todolist.user.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess(){
        User user = new User();
        user.setId(1L);
        user.setUsername("JohnDoe");
        user.setEmail("JohnDoe@steve.com");
        user.setPassword("randompassword");

        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Clean");
        task1.setDescription("Clean the house");
        task1.setStatus(Status.DONE);
        task1.setDueDate(ZonedDateTime.now());
        task1.setOwner(user);

        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        User result = userService.findById(1L);
        assertEquals(result.getId(), user.getId());
    }

    @Test
    public void testFindByIdNotFound(){
        given(userRepository.findById(Mockito.any(Long.class))).willReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, ()->userService.findById(1L));
        assertEquals("Could not find user with Id 1", exception.getMessage());
    }


}