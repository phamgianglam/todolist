package com.example.todolist.service;

import com.example.todolist.dto.profile.ProfilePartialRequestDTO;
import com.example.todolist.model.Profile;
import com.example.todolist.repository.ProfileRepository;
import com.example.todolist.util.Exceptions;
import com.example.todolist.util.Helper;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class ProfileService {

  public ProfileService(
      Helper helper, PasswordEncoder passwordEncoder, ProfileRepository profileRepository) {
    this.helper = helper;
    this.passwordEncoder = passwordEncoder;
    this.profileRepository = profileRepository;
  }

  private final Helper helper;
  private final PasswordEncoder passwordEncoder;

  private final ProfileRepository profileRepository;

  @Value("${app.upload.dir}")
  private String uploadDir;

  public Profile findById(Long userId) {
    Profile profile =
        this.profileRepository
            .findById(userId)
            .orElseThrow(() -> new Exceptions.ObjectNotFoundException("profile"));
    if (helper.isAdmin()) {
      return profile;
    } else {
      if (Objects.equals(helper.getCurrentUserName(), profile.getUsername())) {
        return profile;
      }

      throw new Exceptions.ObjectNotFoundException("profile");
    }
  }

  public Page<Profile> findAll(Specification<Profile> specification, Pageable pageable) {
    if (helper.isAdmin()) {
      return this.profileRepository.findAll(specification, pageable);
    } else {
      return this.profileRepository.findByUsername(helper.getCurrentUserName(), pageable);
    }
  }

  public Profile createProfile(Profile profile) {
    profile.setPassword(passwordEncoder.encode(profile.getPassword()));
    return this.profileRepository.save(profile);
  }

  public Profile partialUpdateProfile(ProfilePartialRequestDTO data, long profileId) {
    Profile profile = this.findById(profileId);

    if (data.email() != null) profile.setEmail(data.email());
    if (data.username() != null) profile.setUsername(data.email());

    profile = this.profileRepository.save(profile);
    return profile;
  }

  public void deleteProfileById(long profileId) {
    this.profileRepository.deleteById(profileId);
  }

  public List<Profile> getByUsername(String username) {
    return this.profileRepository.findByUsername(username);
  }

  public Profile uploadAvatarImage(MultipartFile file, Long id) throws IOException {

    var profile = findById(id);

    var filename = file.getOriginalFilename();
    assert filename != null;
    if (filename.lastIndexOf('.') == -1) {
      throw new IllegalArgumentException("File must have an extension");
    }
    var extension = filename.substring(filename.lastIndexOf("."));
    var uniqueFileName = UUID.randomUUID() + extension;

    Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
    Files.createDirectories(uploadPath);

    Path targetPath = uploadPath.resolve(uniqueFileName);
    file.transferTo(targetPath.toFile());

    profile.setImagePath(uploadDir + uniqueFileName);
    profile = this.profileRepository.save(profile);

    return profile;
  }
}
