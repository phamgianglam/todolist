package com.example.todolist.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.example.todolist.dto.profile.ProfilePartialRequestDTO;
import com.example.todolist.model.Profile;
import com.example.todolist.model.Task;
import com.example.todolist.repository.ProfileRepository;
import com.example.todolist.repository.ProfileSpecification;
import com.example.todolist.util.Exceptions.ObjectNotFoundException;
import com.example.todolist.util.Status;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

  @Mock ProfileRepository profileRepository;

  @InjectMocks ProfileService profileService;

  private List<Profile> profileList;

  @BeforeEach
  void setUp() {
    Profile profile = new Profile();
    profile.setId(1L);
    profile.setUsername("JohnDoe");
    profile.setEmail("JohnDoe@steve.com");
    profile.setPassword("randompassword");

    Task task1 = new Task();
    task1.setId(1L);
    task1.setTitle("Clean");
    task1.setDescription("Clean the house");
    task1.setStatus(Status.DONE);
    task1.setDueDate(ZonedDateTime.now());
    task1.setOwner(profile);

    Profile profile2 = new Profile();
    profile2.setId(1L);
    profile2.setUsername("JohnDoe");
    profile2.setEmail("JohnDoe@steve.com");
    profile2.setPassword("randompassword");

    this.profileList = new ArrayList<>();
    this.profileList.add(profile2);
  }

  @AfterEach
  void tearDown() {}

  @Test
  void testFindByIdSuccess() {

    given(profileRepository.findById(1L)).willReturn(Optional.of(this.profileList.getFirst()));

    Profile result = profileService.findById(1L);
    assertEquals(result.getId(), this.profileList.getFirst().getId());
  }

  @Test
  public void testFindByIdNotFound() {
    given(profileRepository.findById(Mockito.any(Long.class))).willReturn(Optional.empty());

    ObjectNotFoundException exception =
        assertThrows(ObjectNotFoundException.class, () -> profileService.findById(1L));
    assertEquals("Could not find profile", exception.getMessage());
  }

  @Test
  void testfindAll() {
    given(profileRepository.findAll()).willReturn(this.profileList);
    var page = PageRequest.of(0, 10);
    Specification<Profile> specification =
        Specification.where(ProfileSpecification.isAdminUser(false));
    Page<Profile> profiles = this.profileService.findAll(specification, page);
    assertEquals(profiles.toList().size(), this.profileList.size());
  }

  @Test
  void testCreateProfile() {
    var profile = new Profile("TestUser", "TestPassowrd", "TestUser@gmail.com");
    var saveProfile = new Profile("TestUser", "TestPassowrd", "TestUser@gmail.com");
    saveProfile.setId(1L);
    given(profileRepository.save(profile)).willReturn(saveProfile);

    var result = this.profileService.createProfile(profile);
    assertEquals(saveProfile.getId(), result.getId());
  }

  @Test
  void testCreateProfileExisted() {
    var profile = new Profile("TestUser", "TestPassowrd", "TestUser@gmail.com");
    given(profileRepository.save(profile))
        .willThrow(new DataIntegrityViolationException("Unique constraint violation"));

    assertThrows(
        DataIntegrityViolationException.class, () -> profileService.createProfile(profile));
  }

  @Test
  void testUpdateProfile() {
    var oldItem = profileList.getFirst();
    given(profileRepository.findById(1L)).willReturn(Optional.of(oldItem));
    var record = new Profile();
    record.setId(oldItem.getId());
    record.setRole(oldItem.getRole());
    record.setUsername(oldItem.getUsername());
    record.setPassword(oldItem.getPassword());
    var newEmail = "stuff@gmail.com";
    record.setEmail(newEmail);

    given(profileRepository.save(oldItem)).willReturn(record);
    var dto = new ProfilePartialRequestDTO(null, newEmail);
    var result = this.profileService.partialUpdateProfile(dto, 1L);

    assertEquals(result.getId(), record.getId());
    assertEquals(result.getEmail(), record.getEmail());
  }

  @Test
  void testUpdateProfileNotFoundExeption() {
    given(profileRepository.findById(1L)).willReturn(Optional.empty());

    ObjectNotFoundException exception =
        assertThrows(ObjectNotFoundException.class, () -> profileService.findById(1L));
    assertEquals("Could not find profile", exception.getMessage());
  }

  @Test
  void testUpdateProfileIntegrityError() {
    var oldItem = profileList.getFirst();
    given(profileRepository.findById(1L)).willReturn(Optional.of(oldItem));
    given(profileRepository.save(oldItem))
        .willThrow(new DataIntegrityViolationException("Unique constraint violation"));
    var dto = new ProfilePartialRequestDTO(this.profileList.getLast().getUsername(), null);
    assertThrows(
        DataIntegrityViolationException.class, () -> profileService.partialUpdateProfile(dto, 1L));
  }

  @Test
  void testDeleteProfile() {
    profileService.deleteProfileById(1L);
    verify(profileRepository).deleteById(1L);
  }
}
