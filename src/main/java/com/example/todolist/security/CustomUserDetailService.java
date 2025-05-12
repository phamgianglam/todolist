package com.example.todolist.security;

import com.example.todolist.model.Profile;
import com.example.todolist.repository.ProfileRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
  private ProfileRepository profileRepository;

  public CustomUserDetailService(ProfileRepository profileRepository) {
    this.profileRepository = profileRepository;
  }

  @Override
  public CustomUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
    Profile profile =
        profileRepository
            .findByUsername(username)
            .orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username));

    return new CustomUserDetail(profile);
  }
}
