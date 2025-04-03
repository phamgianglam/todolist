package com.example.todolist.rest;

import com.example.todolist.model.Profile;
import com.example.todolist.service.ProfileService;
import com.example.todolist.converter.profile.ProfileRequestDTOToProfileConverter;
import com.example.todolist.converter.profile.ProfileToProfileDTOConverter;
import com.example.todolist.dto.profile.ProfileDTO;
import com.example.todolist.dto.profile.ProfilePartialRequestDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@SecurityRequirement(name = "BearerAuth")
@RequestMapping("/api/v1/profiles")
public class ProfileController {
    private final ProfileService profileService;

    private final ProfileToProfileDTOConverter profileToProfileDTOConverter;

    private final ProfileRequestDTOToProfileConverter profileRequestDTOToProfileConverter;

    public ProfileController(ProfileService profileService,
            ProfileToProfileDTOConverter profileToProfileDTOConverter,
            ProfileRequestDTOToProfileConverter profileRequestDTOToProfileConverter) {
        this.profileService = profileService;
        this.profileToProfileDTOConverter = profileToProfileDTOConverter;
        this.profileRequestDTOToProfileConverter = profileRequestDTOToProfileConverter;
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileDTO> findUseById(@PathVariable long profileId) {

        Profile profile = this.profileService.findById(profileId);
        ProfileDTO profileDTO = this.profileToProfileDTOConverter.convert(profile);
        return ResponseEntity.ok(profileDTO);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProfileDTO>> findAll() {
        List<Profile> profiles = this.profileService.findAll();
        List<ProfileDTO> profilesDTO =
                profiles.stream().map(profileToProfileDTOConverter::convert).toList();
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

}
