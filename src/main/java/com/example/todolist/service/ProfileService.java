package com.example.todolist.service;

import java.util.List;

import com.example.todolist.model.Profile;
import com.example.todolist.repository.ProfileRepository;
import com.example.todolist.util.Exceptions;
import com.example.todolist.util.Helper;
import jakarta.transaction.Transactional;
import org.springframework.security.core.parameters.P;
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
        Profile profile = this.profileRepository.findById(userId)
                .orElseThrow(() -> new Exceptions.ObjectNotFoundException("profile"));
        if (Helper.isAdmin()) {
            return profile;
        } else {
            if (Helper.getCurrentUsername().equals(profile.getUsername())) {
                return profile;
            }

            throw new Exceptions.ObjectNotFoundException("profile");
        }
    }

    public List<Profile> findAll() {
        if (Helper.isAdmin()){
            return this.profileRepository.findAll();
        } else {
            return this.profileRepository.findByUsername(Helper.getCurrentUsername());
        }

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
        Profile profile = this.findById(profileId);
        this.profileRepository.delete(profile);
    }

    public List<Profile> getByUsername(String username) {
        return this.profileRepository.findByUsername(username);
    }
}

