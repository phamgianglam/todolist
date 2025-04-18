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
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
  private PasswordEncoder passwordEncoder;
  private final ProfileService profileService;
  private final AuthDtoToProfileConverter authDtoToProfileConverter;
  private final ProfileRequestDTOToProfileConverter profileRequestDTOToProfileConverter;
  private final JwtUltil jwtUltil;

  public AuthController(
      ProfileService profileService,
      AuthDtoToProfileConverter authDtoToProfileConverter,
      ProfileRequestDTOToProfileConverter profileRequestDTOToProfileConverter,
      JwtUltil jwtUltil,
      PasswordEncoder passwordEncoder) {
    this.profileService = profileService;
    this.authDtoToProfileConverter = authDtoToProfileConverter;
    this.profileRequestDTOToProfileConverter = profileRequestDTOToProfileConverter;
    this.jwtUltil = jwtUltil;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/api/v1/auth/")
  public ResponseEntity<String> auth(@Valid @RequestBody AuthDTO authDTO) {
    Profile requestAuthprofile = authDtoToProfileConverter.convert(authDTO);
    assert requestAuthprofile != null;
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
