package com.example.todolist.converter.profile;

import com.example.todolist.dto.profile.ProfileRequestDTO;
import com.example.todolist.model.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProfileRequestDTOToProfileConverter implements Converter<ProfileRequestDTO, Profile> {
  @Override
  public Profile convert(ProfileRequestDTO source) {
      return new Profile(source.username(), source.password(), source.email());
  }
}
