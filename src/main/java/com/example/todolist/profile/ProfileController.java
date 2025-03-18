package com.example.todolist.profile;

import com.example.todolist.profile.converter.ProfileRequestDTOToProfileConverter;
import com.example.todolist.profile.converter.ProfileToProfileDTOConverter;
import com.example.todolist.profile.dto.ProfileDTO;
import com.example.todolist.profile.dto.ProfileRequestDTO;
import com.example.todolist.system.Result;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
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

    @GetMapping("/api/v1/profiles/{profileId}")
    public Result findUseById(@PathVariable long profileId) {

        Profile profile = this.profileService.findById(profileId);
        ProfileDTO profileDTO = this.profileToProfileDTOConverter.convert(profile);
        return new Result(profileDTO, 200, "Found profile");
    }

    @GetMapping("/api/v1/profiles/")
    public Result findAll(){
        List<Profile> profiles = this.profileService.findAll();
        List<ProfileDTO> profilesDTO = profiles.stream().map(profileToProfileDTOConverter::convert).toList();
        return  new Result(profilesDTO, 200, "Found profiles");
    }

    @PostMapping("/api/v1/profiles/")
    public Result createProfile(@Valid @RequestBody ProfileRequestDTO profileRequestDTO) {
        Profile profile = this.profileRequestDTOToProfileConverter.convert(profileRequestDTO);
        profile = this.profileService.createProfile(profile);
        ProfileDTO profileDTO = this.profileToProfileDTOConverter.convert(profile);
        return new Result(profileDTO, 201,"object created");
    }
}
