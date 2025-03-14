package com.example.todolist.user;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final UserRepository userRepository;

    public User findById(Long userId){
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new Exception.UserNotFoundException(userId));
    }
}
