package com.example.todolist.repository;

import com.example.todolist.model.Profile;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository
    extends JpaRepository<Profile, Long>, JpaSpecificationExecutor<Profile> {
  Page<Profile> findByUsername(String username, Pageable pageable);

  List<Profile> findByUsername(String username);
}
