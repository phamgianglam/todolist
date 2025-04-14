package com.example.todolist.converter.profile;

import com.example.todolist.dto.profile.ProfileDTO;
import com.example.todolist.model.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProfileToProfileDTOConverter implements Converter<Profile, ProfileDTO> {

  @Override
  public ProfileDTO convert(Profile source) {
      return new ProfileDTO(
          source.getId(), source.getUsername(), source.getEmail(), source.getTasks().size(), source.getImagePath());
  }
}
