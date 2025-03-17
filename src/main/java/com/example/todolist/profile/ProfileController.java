package com.example.todolist.profile;

import com.example.todolist.profile.converter.ProfileConverter;
import com.example.todolist.profile.dto.ProfileDTO;
import com.example.todolist.system.Result;
import com.example.todolist.task.DTO.TaskDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class ProfileController {
    private final ProfileService profileService;

    private final ProfileConverter profileConverter;

    public ProfileController(ProfileService profileService, ProfileConverter profileConverter) {
        this.profileService = profileService;
        this.profileConverter = profileConverter;
    }

    @GetMapping("/api/v1/profiles/{profileId}")
    public Result findUseById(@PathVariable long profileId) {

        Profile profile = this.profileService.findById(profileId);
        ProfileDTO profileDTO = this.profileConverter.convert(profile);
        return new Result(profileDTO, 200, "Found profile");
    }

    @GetMapping("/api/v1/profiles/")
    public Result findAll(){
        List<Profile> profiles = this.profileService.findAll();
        List<ProfileDTO> profilesDTO = profiles.stream().map(profileConverter::convert).toList();
        return  new Result(profilesDTO, 200, "Found profiles");
    }
}
