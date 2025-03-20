package com.example.todolist.profile;

import com.example.todolist.helper.Status;
import com.example.todolist.task.Task;
import com.example.todolist.profile.ProfileException.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    ProfileRepository profileRepository;

    @InjectMocks
    ProfileService profileService;

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

        this.profileList = new ArrayList<>();
        this.profileList.add(profile);
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

        UserNotFoundException exception =
                assertThrows(UserNotFoundException.class, () -> profileService.findById(1L));
        assertEquals("Could not find user with Id 1", exception.getMessage());
    }


    @Test
    void testfindAll() {
        given(profileRepository.findAll()).willReturn(this.profileList);

        List<Profile> profiles = this.profileService.findAll();
        assertEquals(profiles.size(), this.profileList.size());
    }
}
