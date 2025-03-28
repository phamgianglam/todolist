package com.example.todolist.service;

import java.util.List;

import com.example.todolist.model.Profile;
import com.example.todolist.repository.ProfileRepository;
import com.example.todolist.util.Exceptions;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.example.todolist.dto.profile.ProfilePartialRequestDTO;

@Service
@Transactional
public class ProfileService {

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    private final ProfileRepository profileRepository;

    public Profile findById(Long userId) {
        return this.profileRepository.findById(userId)
                .orElseThrow(() -> new Exceptions.ObjectNotFoundException("profile"));
    }

    public List<Profile> findAll() {
        return this.profileRepository.findAll();
    }

    public Profile createProfile(Profile profile) {
        return this.profileRepository.save(profile);
    }

    public Profile partialUpdateProfile(ProfilePartialRequestDTO data, long profileId) {
        Profile profile = this.findById(profileId);

        if (data.email() != null)
            profile.setEmail(data.email());
        if (data.username() != null)
            profile.setUsername(data.email());

        profile = this.profileRepository.save(profile);
        return profile;
    }

    public void deleteProfileById(long profileId) {
        this.profileRepository.deleteById(profileId);
    }

    public List<Profile> getByUsername(String username) {
        return this.profileRepository.findByUsername(username);
    }
}

