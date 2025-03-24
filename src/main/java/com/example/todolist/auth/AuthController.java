package com.example.todolist.auth;

import java.util.List;
import com.example.todolist.auth.Converter.AuthDtoToProfileConverter;
import com.example.todolist.auth.dto.AuthDTO;
import com.example.todolist.profile.Profile;
import com.example.todolist.profile.ProfileService;
import com.example.todolist.profile.converter.ProfileRequestDTOToProfileConverter;
import com.example.todolist.profile.dto.ProfileRequestDTO;
import com.example.todolist.util.JwtUltil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {
    private ProfileService profileService;
    private AuthDtoToProfileConverter authDtoToProfileConverter;
    private ProfileRequestDTOToProfileConverter profileRequestDTOToProfileConverter;
    private JwtUltil jwtUltil;

    public AuthController(ProfileService profileService,
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
        if (profiles.isEmpty())
            return ResponseEntity.notFound().build();

        Profile profile = profiles.getFirst();
        if (!profile.getPassword().equals(requestAuthprofile.getPassword()))
            return ResponseEntity.notFound().build();
        String token = this.jwtUltil.createToken(profile);

        return ResponseEntity.ok(token);
    }


    @RequestMapping("/api/v1/register")
    public ResponseEntity register(ProfileRequestDTO dto) {
        Profile profile = profileRequestDTOToProfileConverter.convert(dto);
        profile = profileService.createProfile(profile);
        return ResponseEntity.ok("Successfully create profile");
    }
}
