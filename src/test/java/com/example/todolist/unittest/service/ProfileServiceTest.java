package com.example.todolist.unittest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.example.todolist.dto.profile.ProfilePartialRequestDTO;
import com.example.todolist.model.Permission;
import com.example.todolist.model.Profile;
import com.example.todolist.model.Task;
import com.example.todolist.repository.PermissionRepository;
import com.example.todolist.repository.ProfileRepository;
import com.example.todolist.repository.ProfileSpecification;
import com.example.todolist.service.ProfileService;
import com.example.todolist.util.Exceptions.ObjectNotFoundException;
import com.example.todolist.util.Helper;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

  @Mock ProfileRepository profileRepository;
  @Mock PermissionRepository permissionRepository;
  @Mock PasswordEncoder passwordEncoder;
  @Mock Helper helper;
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
  void tearDown() {
    // add tear down logic here
  }

  @Test
  void testFindByIdSuccess() {

    given(profileRepository.findById(1L)).willReturn(Optional.of(this.profileList.getFirst()));
    given(helper.isAdmin()).willReturn(Boolean.TRUE);
    Profile result = profileService.findById(1L);
    assertEquals(result.getId(), this.profileList.getFirst().getId());
  }

  @Test
  void testFindByIdNotFound() {
    given(profileRepository.findById(Mockito.any(Long.class))).willReturn(Optional.empty());
    ObjectNotFoundException exception =
        assertThrows(ObjectNotFoundException.class, () -> profileService.findById(1L));
    assertEquals("Could not find profile", exception.getMessage());
  }

  @Test
  void testfindAll() {
    var page = PageRequest.of(0, 10);
    Specification<Profile> specification =
        Specification.where(ProfileSpecification.isAdminUser(false));
    var pageResult = new PageImpl<>(this.profileList, page, 10);
    given(profileRepository.findAll(any(Specification.class), any(Pageable.class)))
        .willReturn(pageResult);
    given(helper.isAdmin()).willReturn(Boolean.TRUE);
    Page<Profile> profiles = this.profileService.findAll(specification, page);
    assertEquals(profiles.toList().size(), this.profileList.size());
  }

  @Test
  void testCreateProfile() {
    var profile = new Profile("TestUser", "TestPassowrd", "TestUser@gmail.com");
    var saveProfile = new Profile("TestUser", "TestPassowrd", "TestUser@gmail.com");
    saveProfile.setId(1L);
    given(profileRepository.save(profile)).willReturn(saveProfile);
    given(passwordEncoder.encode(any(String.class))).willReturn("encodedPassword");
    given(permissionRepository.findByName(any(String.class)))
        .willReturn(Optional.of(new Permission("PROFILE_READ_OWN")));
    var result = this.profileService.createProfile(profile);
    assertEquals(saveProfile.getId(), result.getId());
  }

  @Test
  void testCreateProfileExisted() {
    var profile = new Profile("TestUser", "TestPassowrd", "TestUser@gmail.com");
    given(profileRepository.save(profile))
        .willThrow(new DataIntegrityViolationException("Unique constraint violation"));
    given(passwordEncoder.encode(any(String.class))).willReturn("encodedPassword");
    given(permissionRepository.findByName(any(String.class)))
        .willReturn(Optional.of(new Permission("PROFILE_READ_OWN")));
    assertThrows(
        DataIntegrityViolationException.class, () -> profileService.createProfile(profile));
  }

  @Test
  void testUpdateProfile() {
    var oldItem = profileList.getFirst();
    given(profileRepository.findById(1L)).willReturn(Optional.of(oldItem));
    given(helper.isAdmin()).willReturn(Boolean.TRUE);
    var profile = new Profile();
    profile.setId(oldItem.getId());
    profile.setUsername(oldItem.getUsername());
    profile.setPassword(oldItem.getPassword());
    var newEmail = "stuff@gmail.com";
    profile.setEmail(newEmail);

    given(profileRepository.save(oldItem)).willReturn(profile);
    var dto = new ProfilePartialRequestDTO(null, newEmail);
    var result = this.profileService.partialUpdateProfile(dto, 1L);

    assertEquals(result.getId(), profile.getId());
    assertEquals(result.getEmail(), profile.getEmail());
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
    given(helper.isAdmin()).willReturn(Boolean.TRUE);
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
