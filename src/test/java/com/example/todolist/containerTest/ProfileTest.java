package com.example.todolist.containerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.todolist.model.Profile;
import com.example.todolist.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ProfileTest extends IntegrationTestSetup {
  @Autowired private ProfileRepository profileRepository;

  @Test
  void testCreateProfile() {
    var profile =
        Profile.builder()
            .username("usertest1")
            .password("abcpassword")
            .email("abc@gmail.com")
            .build();
    var savedProfile = profileRepository.save(profile);
    assertEquals(savedProfile.getUsername(), profile.getUsername());
  }

  @Test
  void testFetchProfile() {
    var profile =
        Profile.builder()
            .username("usertest")
            .password("abcpassword")
            .email("abc@gmail.com")
            .build();
    var savedProfile = profileRepository.save(profile);
    var fetchedProfile = profileRepository.findById(savedProfile.getId()).orElse(null);
    assertEquals(savedProfile.getUsername(), fetchedProfile.getUsername());
  }

  @Test
  void testFetchProfileNotExist() {
    var fetchedProfile = profileRepository.findById(999L).orElse(null);
    assertEquals(null, fetchedProfile);
  }

  @Test
  void testDeleteProfile() {
    var profile = Profile.builder().username("2").password("2").email("2@gmail.com").build();
    var savedProfile = profileRepository.save(profile);
    profileRepository.deleteById(savedProfile.getId());
    profile = profileRepository.findById(savedProfile.getId()).orElse(null);
    assertNull(profile);
  }

  @Test
  void testFetchProfiles() {
    var profiles = profileRepository.findAll();
    assertEquals(2, profiles.size());
  }
}
