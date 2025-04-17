package com.example.todolist.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import com.example.todolist.dto.profile.ProfilePartialRequestDTO;
import com.example.todolist.model.Profile;
import com.example.todolist.service.ProfileService;
import com.example.todolist.util.Exceptions;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ProfileControllerTest {

  @Autowired MockMvc mockMvc;

  @MockitoBean ProfileService profileService;

  private static final ObjectMapper mapper = new ObjectMapper();

  List<Profile> profiles;

  @BeforeEach
  void setUp() {
    this.profiles = new ArrayList<Profile>();
    Profile profile = new Profile();
    profile.setId(1L);
    profile.setUsername("JohnDoe");
    profile.setEmail("JohnDoe@steve.com");
    profile.setPassword("randompassword");

    Profile profile1 = new Profile();
    profile1.setId(2L);
    profile1.setUsername("JohnDoe");
    profile1.setEmail("JohnDoe@steve.com");
    profile1.setPassword("randompassword");

    Profile profile2 = new Profile();
    profile2.setId(3L);
    profile2.setUsername("JohnDoe");
    profile2.setEmail("JohnDoe@steve.com");
    profile2.setPassword("randompassword");

    this.profiles.add(profile);
    this.profiles.add(profile1);
    this.profiles.add(profile2);
  }

  @AfterEach
  void tearDown() {}

  @Test
  void testFindUseById() throws Exception {
    given(this.profileService.findById(1L)).willReturn(this.profiles.getFirst());
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get("/api/v1/profiles/1").accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
  }

  @Test
  void testFindUseByIdNotFound() throws Exception {
    given(this.profileService.findById(1L))
        .willThrow(new Exceptions.ObjectNotFoundException("profile"));

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get("/api/v1/profiles/1").accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(MockMvcResultMatchers.content().string("Object not found"));
  }

  @Test
  void testfindAll() throws Exception {
    var page = PageRequest.of(0, 10, Sort.Direction.ASC, "id");
    var pageResult = new PageImpl<>(this.profiles, page, 10);
    given(this.profileService.findAll(any(Specification.class), any(Pageable.class)))
        .willReturn(pageResult);

    this.mockMvc
        .perform(MockMvcRequestBuilders.get("/api/v1/profiles/").accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(this.profiles.size())));
  }

  @Test
  void testPatchProfile() throws Exception {
    Profile profile = new Profile("test", "password", "password@email.com");
    profile.setId(1L);

    var dtoObject = new ProfilePartialRequestDTO(null, "password@email.com");
    given(
            this.profileService.partialUpdateProfile(
                any(ProfilePartialRequestDTO.class), any(Long.class)))
        .willReturn(profile);
    this.mockMvc
        .perform(
            MockMvcRequestBuilders.patch("/api/v1/profiles/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dtoObject)))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  void testDeleteById() throws Exception {
    doNothing().when(profileService).deleteProfileById(1L);
    mockMvc
        .perform(MockMvcRequestBuilders.delete("/api/v1/profiles/1"))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }
}
