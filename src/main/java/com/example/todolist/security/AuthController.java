package com.example.todolist.security;

import com.example.todolist.converter.auth.AuthDtoToProfileConverter;
import com.example.todolist.converter.profile.ProfileRequestDTOToProfileConverter;
import com.example.todolist.dto.auth.AuthDTO;
import com.example.todolist.dto.profile.ProfileRequestDTO;
import com.example.todolist.model.Profile;
import com.example.todolist.service.ProfileService;
import com.example.todolist.util.JwtUltil;
import jakarta.validation.Valid;
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
  private final CustomUserDetailService customUserDetailService;

  public AuthController(
      AuthDtoToProfileConverter authDtoToProfileConverter,
      PasswordEncoder passwordEncoder,
      ProfileService profileService,
      ProfileRequestDTOToProfileConverter profileRequestDTOToProfileConverter,
      JwtUltil jwtUltil,
      CustomUserDetailService customUserDetailService) {
    this.authDtoToProfileConverter = authDtoToProfileConverter;
    this.passwordEncoder = passwordEncoder;
    this.profileService = profileService;
    this.profileRequestDTOToProfileConverter = profileRequestDTOToProfileConverter;
    this.jwtUltil = jwtUltil;
    this.customUserDetailService = customUserDetailService;
  }

  @PostMapping("/api/v1/auth/")
  public ResponseEntity<String> auth(@Valid @RequestBody AuthDTO authDTO) {
    Profile requestAuthprofile = authDtoToProfileConverter.convert(authDTO);
    assert requestAuthprofile != null;
    CustomUserDetail customUserDetail =
        customUserDetailService.loadUserByUsername(authDTO.username());
    if (!customUserDetail.isAccountNonLocked())
      return ResponseEntity.status(400).body("Account is locked");

    if (!passwordEncoder.matches(authDTO.password(), customUserDetail.getPassword()))
      return ResponseEntity.status(400).body("Password is incorrect");

    String token = this.jwtUltil.createToken(customUserDetail);

    return ResponseEntity.ok(token);
  }

  @PostMapping("/api/v1/register")
  public ResponseEntity<String> register(@Valid @RequestBody ProfileRequestDTO dto) {
    Profile profile = profileRequestDTOToProfileConverter.convert(dto);
    profileService.createProfile(profile);
    return ResponseEntity.ok("Successfully create profile");
  }
}
