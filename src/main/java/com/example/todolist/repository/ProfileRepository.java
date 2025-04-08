package com.example.todolist.repository;

import com.example.todolist.model.Profile;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
  List<Profile> findByUsername(String username);
}
