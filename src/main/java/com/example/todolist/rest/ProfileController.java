package com.example.todolist.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.example.todolist.converter.profile.ProfileToProfileDTOConverter;
import com.example.todolist.dto.profile.ProfileDTO;
import com.example.todolist.dto.profile.ProfilePartialRequestDTO;
import com.example.todolist.model.Profile;
import com.example.todolist.repository.ProfileSpecification;
import com.example.todolist.service.ProfileService;
import com.example.todolist.util.Exceptions;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@SecurityRequirement(name = "BearerAuth")
@RequestMapping("/api/v1/profiles")
public class ProfileController {
  private final ProfileService profileService;

  private final ProfileToProfileDTOConverter profileToProfileDTOConverter;

  public ProfileController(
      ProfileService profileService, ProfileToProfileDTOConverter profileToProfileDTOConverter) {
    this.profileService = profileService;
    this.profileToProfileDTOConverter = profileToProfileDTOConverter;
  }

  @GetMapping("/{profileId}")
  public ResponseEntity<ProfileDTO> findUseById(@PathVariable long profileId) {

    Profile profile = this.profileService.findById(profileId);
    ProfileDTO profileDTO = this.profileToProfileDTOConverter.convert(profile);
    return ResponseEntity.ok(profileDTO);
  }

  @GetMapping("/")
  public ResponseEntity<Page<ProfileDTO>> findAll(
      @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)
          @ParameterObject
          Pageable pageable, @Nullable @ParameterObject Boolean isAdmin) {
    Specification<Profile> specification = Specification.where(ProfileSpecification.isAdminUser(isAdmin));
    Page<Profile> profiles = this.profileService.findAll(specification, pageable);
    Page<ProfileDTO> profilesDTO = profiles.map(profileToProfileDTOConverter::convert);
    return ResponseEntity.ok(profilesDTO);
  }

  @PatchMapping("/{profileId}")
  public ResponseEntity<Void> patchProfile(
      @Valid @RequestBody ProfilePartialRequestDTO profilePartialRequestDTO,
      @PathVariable long profileId) {

    this.profileService.partialUpdateProfile(profilePartialRequestDTO, profileId);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{profileId}")
  public ResponseEntity<Void> deleteProfile(@PathVariable long profileId) {
    this.profileService.deleteProfileById(profileId);

    return ResponseEntity.noContent().build();
  }

  @PostMapping(path="/{profileId}/images/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ProfileDTO> uploadAvatarImage(@PathVariable Long profileId, @RequestParam("file")MultipartFile file) throws IOException {
      if (file.isEmpty()){
        throw new Exceptions.BadRequestException("Image is empty");
      }

      var profile =  this.profileToProfileDTOConverter.convert(profileService.uploadAvatarImage(file, profileId));

      return ResponseEntity.status(200).body(profile);
  }
}
