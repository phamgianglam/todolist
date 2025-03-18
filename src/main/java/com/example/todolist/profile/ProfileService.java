package com.example.todolist.profile;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProfileService {

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    private final ProfileRepository profileRepository;

    public Profile findById(Long userId){
        return this.profileRepository.findById(userId)
                .orElseThrow(() -> new ProfileException.UserNotFoundException(userId));
    }

    public List<Profile> findAll() {
        return this.profileRepository.findAll();
    }

    public Profile createProfile(Profile profile) {
        return this.profileRepository.save(profile);
    }
}

