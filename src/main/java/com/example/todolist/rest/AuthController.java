package com.example.todolist.rest;

import com.example.todolist.converter.auth.AuthDtoToProfileConverter;
import com.example.todolist.converter.profile.ProfileRequestDTOToProfileConverter;
import com.example.todolist.dto.auth.AuthDTO;
import com.example.todolist.dto.profile.ProfileRequestDTO;
import com.example.todolist.model.Profile;
import com.example.todolist.service.ProfileService;
import com.example.todolist.util.JwtUltil;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
  @Autowired private PasswordEncoder passwordEncoder;
  private final ProfileService profileService;
  private final AuthDtoToProfileConverter authDtoToProfileConverter;
  private final ProfileRequestDTOToProfileConverter profileRequestDTOToProfileConverter;
  private final JwtUltil jwtUltil;

  public AuthController(
      ProfileService profileService,
      AuthDtoToProfileConverter authDtoToProfileConverter,
      ProfileRequestDTOToProfileConverter profileRequestDTOToProfileConverter,
      JwtUltil jwtUltil) {
    this.profileService = profileService;
    this.authDtoToProfileConverter = authDtoToProfileConverter;
    this.profileRequestDTOToProfileConverter = profileRequestDTOToProfileConverter;
    this.jwtUltil = jwtUltil;
  }

  @PostMapping("/api/v1/auth/")
  public ResponseEntity<String> auth(@RequestBody AuthDTO authDTO) {
    Profile requestAuthprofile = authDtoToProfileConverter.convert(authDTO);
    List<Profile> profiles = profileService.getByUsername(requestAuthprofile.getUsername());
    if (profiles.isEmpty()) return ResponseEntity.notFound().build();

    Profile profile = profiles.getFirst();
    if (!passwordEncoder.matches(authDTO.password(), profile.getPassword()))
      return ResponseEntity.notFound().build();
    String token = this.jwtUltil.createToken(profile);

    return ResponseEntity.ok(token);
  }

  @PostMapping("/api/v1/register")
  public ResponseEntity<String> register(@Valid @RequestBody ProfileRequestDTO dto) {
    Profile profile = profileRequestDTOToProfileConverter.convert(dto);
    profileService.createProfile(profile);
    return ResponseEntity.ok("Successfully create profile");
  }
}
